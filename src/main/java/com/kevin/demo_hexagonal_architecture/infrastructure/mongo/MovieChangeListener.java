package com.kevin.demo_hexagonal_architecture.infrastructure.mongo;

import com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.entities.MovieEntity;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Objects;

@Component
@AllArgsConstructor
@Slf4j
public class MovieChangeListener {

    private final ReactiveMongoTemplate mongoTemplate;
    private final MongoConverter mongoConverter;

    @PostConstruct
    public void init() {
        waitForMongoPrimary()
                .thenMany(startChangeStream())
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(
                        log::info,
                        error -> log.error("❌ Error en el ChangeStream: {}", error.getMessage())
                );
    }

    private Mono<Void> waitForMongoPrimary() {
        return Mono.defer(() ->
                        mongoTemplate.executeCommand("{ isMaster: 1 }")
                                .flatMap(result -> {
                                    boolean isPrimary = result.getBoolean("ismaster", false);
                                    if (isPrimary) {
                                        log.info("✅ Mongo está en estado PRIMARY, iniciando ChangeStream...");
                                        return Mono.empty();
                                    } else {
                                        log.warn("⏳ Mongo no está listo (no es PRIMARY), reintentando...");
                                        return Mono.error(new IllegalStateException("Mongo no es PRIMARY aún"));
                                    }
                                }))
                .retryWhen(Retry.backoff(10, Duration.ofSeconds(1))
                        .doBeforeRetry(retrySignal -> log.warn("Reintentando conexión a MongoDB...")))
                .doOnError(e -> log.error("❌ No se pudo conectar con Mongo como PRIMARY tras varios intentos"))
                .then();
    }


    private Flux<String> startChangeStream() {
        return mongoTemplate.changeStream("movies", ChangeStreamOptions.empty(), Document.class)
                .map(this::convertChangeStreamDocument);
    }

    private String convertChangeStreamDocument(ChangeStreamEvent<Document> event) {
        Document document = Objects.requireNonNull(event.getRaw()).getFullDocument();
        MovieEntity movieEntity = mongoConverter.read(MovieEntity.class, document);
        return "🎬 Change Detected: " + movieEntity;
    }
}

package com.kevin.demo_hexagonal_architecture.infrastructure.mongo;

import com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.entities.MovieEntity;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.OperationType;
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

import java.util.Objects;

@Component
@AllArgsConstructor
@Slf4j
public class MovieChangeListener {
    private final ReactiveMongoTemplate mongoTemplate;
    private final MongoConverter mongoConverter;

    @PostConstruct
    public void init() {
        mongoTemplate.changeStream("movies", ChangeStreamOptions.empty(), Document.class)
                .map(this::convertChangeStreamDocument)
                .subscribe(log::info);
    }

    private String convertChangeStreamDocument(ChangeStreamEvent<Document> event) {
        Document document = Objects.requireNonNull(event.getRaw()).getFullDocument();
        assert document != null;
        MovieEntity movieEntity = mongoConverter.read(MovieEntity.class, document);
        return "Change Detected: " + movieEntity;
    }
}

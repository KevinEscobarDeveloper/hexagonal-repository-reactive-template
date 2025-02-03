package com.kevin.demo_hexagonal_architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class DemoHexagonalArchitectureApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoHexagonalArchitectureApplication.class, args);
	}

}

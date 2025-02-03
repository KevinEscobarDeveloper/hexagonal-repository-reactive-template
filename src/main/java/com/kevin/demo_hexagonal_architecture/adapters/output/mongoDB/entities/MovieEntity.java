package com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movies")
public class MovieEntity {
        @Id
        private Long id;

        @Indexed(unique = true)
        @Field(name = "title")
        private String title;

        @Field(name = "description")
        private String description;

        @Field(name = "release_date")
        private LocalDate releaseDate;

        @Field(name = "director_name")
        private String directorName;

        @CreatedDate
        @Field(name = "created_date")
        private LocalDate createdDate;

        @LastModifiedDate
        @Field(name = "modified_date")
        private LocalDate modifiedDate;
}

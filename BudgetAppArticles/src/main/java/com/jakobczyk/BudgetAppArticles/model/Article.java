package com.jakobczyk.BudgetAppArticles.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;


@Document(collection = "articles")
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Data
public class Article {
    @Id
    private String id;
    private String userId;
    private String title;
    private String description;
    private Binary image;
    private Instant createdOn;
    private Boolean approved;
    private Integer stage;
    private String adminNote;
}

package com.jakobczyk.BudgetAppArticles.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleResponse {
    private String id;
    private String title;
    private String description;
    private Binary image;
    private Instant createdOn;
    private String userId;
    private boolean approved;
    private Integer stage;
    private String adminNote;
}

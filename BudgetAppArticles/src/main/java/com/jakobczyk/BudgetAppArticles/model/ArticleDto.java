package com.jakobczyk.BudgetAppArticles.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Data
public class ArticleDto {
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

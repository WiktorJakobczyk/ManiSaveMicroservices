package com.jakobczyk.BudgetAppArticles.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleUpdateRequest {
    @NotEmpty
    @NotNull
    @Length(min = 6)
    private String title;
    private Integer stage;
    private boolean approved;
    @NotEmpty
    @NotNull
    @Length(min = 24)
    private String description;
    private String adminNote;
}

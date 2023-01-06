package com.jakobczyk.BudgetAppArticles.exception;

public class ArticleNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -8231635078242610369L;

    public ArticleNotFoundException(String message) {
        super(message);
    }
}

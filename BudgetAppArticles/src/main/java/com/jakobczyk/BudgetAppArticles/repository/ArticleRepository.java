package com.jakobczyk.BudgetAppArticles.repository;

import com.jakobczyk.BudgetAppArticles.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository  extends MongoRepository<Article, String> {
    Optional<Article> findByTitle(String title);

    @Query("{'approved' : true}")
    Page<Article> findAll(Pageable pageable);

    Optional<List<Article>> findAllByUserId(String userId);
}

package com.jakobczyk.BudgetAppArticles.controller;

import com.jakobczyk.BudgetAppArticles.exception.DefaultImageException;
import com.jakobczyk.BudgetAppArticles.model.ArticleDto;
import com.jakobczyk.BudgetAppArticles.model.request.ArticleRequest;
import com.jakobczyk.BudgetAppArticles.model.request.ArticleUpdateRequest;
import com.jakobczyk.BudgetAppArticles.model.response.ArticleResponse;
import com.jakobczyk.BudgetAppArticles.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getArticles( @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "3") int size){
        Map<String, Object> allArticles = articleService.getAllArticles(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(allArticles);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArticleResponse>> getArticlesAll(){
        List<ArticleResponse> allArticles = articleService.getAllArticles();
        return ResponseEntity.status(HttpStatus.OK).body(allArticles);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable String articleId){
        ArticleResponse article = articleService.getArticle(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(article);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ArticleResponse>> getArticlesByUser(@PathVariable String userId){
        List<ArticleResponse> article = articleService.getArticlesByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(article);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<String> updateArticle(@PathVariable String articleId, @Valid @RequestBody ArticleUpdateRequest articleRequest) {
        ModelMapper modelMapper = new ModelMapper();
        ArticleDto articleDto = modelMapper.map(articleRequest, ArticleDto.class);
        articleService.updateArticle(articleDto, articleId);
        return ResponseEntity.status(HttpStatus.OK).body("Article updated!");
    }

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@Valid @RequestBody ArticleRequest articleRequest) throws DefaultImageException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ArticleDto articleDto = modelMapper.map(articleRequest, ArticleDto.class);
        ArticleResponse returnValue = null;
        try{
            ArticleDto createdArticle = articleService.createArticle(articleDto);
            returnValue = modelMapper.map(createdArticle, ArticleResponse.class);
        }catch (IOException e){
            throw new DefaultImageException("Cannot create default image");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<String> addPhotoArticle(@PathVariable("id") String id, @RequestParam("image") MultipartFile file) throws Exception {
        articleService.addPhoto(id, file);
        return ResponseEntity.status(HttpStatus.CREATED).body("Image changed successfully");
    }
}

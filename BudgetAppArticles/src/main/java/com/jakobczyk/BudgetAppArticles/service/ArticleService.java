package com.jakobczyk.BudgetAppArticles.service;

import com.jakobczyk.BudgetAppArticles.exception.ArticleNotFoundException;
import com.jakobczyk.BudgetAppArticles.exception.DefaultImageException;
import com.jakobczyk.BudgetAppArticles.model.Article;
import com.jakobczyk.BudgetAppArticles.model.ArticleDto;
import com.jakobczyk.BudgetAppArticles.model.request.ArticleRequest;
import com.jakobczyk.BudgetAppArticles.model.response.ArticleResponse;
import com.jakobczyk.BudgetAppArticles.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ResourceLoader resourceLoader;


    public Map<String, Object> getAllArticles(int page, int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("createdOn").descending());
        Page<Article> pageArticles = articleRepository.findAll(paging);
        List<Article> articles = pageArticles.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("articles", articles);
        response.put("currentPage", pageArticles.getNumber());
        response.put("totalItems", pageArticles.getTotalElements());
        response.put("totalPages", pageArticles.getTotalPages());
        return response;
    }

    public List<ArticleResponse> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(article -> new ModelMapper().map(article, ArticleResponse.class)).collect(Collectors.toList());
    }


    public ArticleDto createArticle(ArticleDto articleDto) throws IOException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Article article = modelMapper.map(articleDto, Article.class);
        CommonsMultipartFile multipartFile = getDefaultImage();

        article.setImage(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
        article.setCreatedOn(Instant.now());
        article.setStage(1);
        articleRepository.save(article);
        return modelMapper.map(article, ArticleDto.class);
    }

    private CommonsMultipartFile getDefaultImage() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:default.jpg");
        File file = resource.getFile();
        FileItem fileItem = new DiskFileItemFactory().createItem("file",
                Files.probeContentType(file.toPath()), false, file.getName());
        try (InputStream in = new FileInputStream(file); OutputStream out = fileItem.getOutputStream()) {
            in.transferTo(out);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        CommonsMultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }

    public void addPhoto(String id, MultipartFile file) throws IOException {
        Article article = articleRepository.findById(id).orElseThrow(()-> {
            throw new ArticleNotFoundException("Article not found");
        });
        article.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        article.setStage(3);
        articleRepository.save(article);
    }

    public ArticleResponse getArticle(String articleId){
        Article article = articleRepository.findById(articleId).orElseThrow(() -> {
            throw new ArticleNotFoundException("No article found");
        });
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(article, ArticleResponse.class);
    }

    public List<ArticleResponse> getArticlesByUser(String userId){
        List<Article> articles = articleRepository.findAllByUserId(userId).orElseThrow(() -> {
            throw new ArticleNotFoundException("No category found");
        });
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(articles, new TypeToken<List<ArticleResponse>>() {}.getType());
    }

    public void updateArticle(ArticleDto articleDto, String articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(()-> {
            throw new ArticleNotFoundException("Article not found");
        });
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setStage(articleDto.getStage());
        article.setApproved(articleDto.getApproved());
        if(articleDto.getApproved()){
            article.setCreatedOn(Instant.now());
        }
        article.setAdminNote(articleDto.getAdminNote());
        articleRepository.save(article);
    }
}

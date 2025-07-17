package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.Article;
import com.pmq.vnnewsvoice.repository.ArticleRepository;
import com.pmq.vnnewsvoice.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Override
    public ArticleRepository addArticle(Article article) {
        return null;
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Article> getArticleBySlugAndId(String slug, Long id) {
        return Optional.empty();
    }

    @Override
    public List<Article> getArticlesByCategoryId(Long categoryId) {
        return List.of();
    }

    @Override
    public List<Article> searchArticles(Map<String, String> filters, Map<String, String> params) {
        return List.of();
    }

    @Override
    public Article updateArticle(Article article) {
        return null;
    }

    @Override
    public boolean deleteArticle(Long id) {
        return false;
    }

    @Override
    public long countArticles() {
        return 0;
    }

    @Override
    public long countSearchArticles(Map<String, String> filters, Map<String, String> params) {
        return 0;
    }
}

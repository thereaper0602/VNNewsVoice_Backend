package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.Article;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ArticleRepository {
    Article addArticle(Article article);

    Optional<Article> getArticleById(Long id);
    Optional<Article> getArticleBySlugAndId(String slug, Long id);
    List<Article> getArticlesByCategoryId(Long categoryId);
    List<Article> searchArticles(Map<String, String> filters, Map<String, String> params);
    List<Article> getArticles(Map<String, String> params);
    Article updateArticle(Article article);

    boolean deleteArticle(Long id);

    long countArticles();
    long countSearchArticles(Map<String, String> filters);
    boolean isEditorOfArticle(Long editorId, Long articleId);

    List<Predicate> buildSearchPredicates(Map<String, String> filters, CriteriaBuilder builder, Root<Article> root);
}

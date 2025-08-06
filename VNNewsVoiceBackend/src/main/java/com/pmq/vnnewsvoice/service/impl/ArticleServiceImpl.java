package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.Article;
import com.pmq.vnnewsvoice.pojo.Editor;
import com.pmq.vnnewsvoice.pojo.UserInfo;
import com.pmq.vnnewsvoice.repository.ArticleRepository;
import com.pmq.vnnewsvoice.repository.UserInfoRepository;
import com.pmq.vnnewsvoice.service.ArticleService;
import com.pmq.vnnewsvoice.service.EditorService;
import com.pmq.vnnewsvoice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private EditorService editorService;

    @Override
    public Article addArticle(Article article) {
        if (!isArticleValid(article)) {
            throw new IllegalArgumentException("Invalid article data");
        }
        return articleRepository.addArticle(article);
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return articleRepository.getArticleById(id);
    }

    @Override
    public Optional<Article> getArticleBySlugAndId(String slug, Long id) {
        if (slug == null || slug.isEmpty() || id == null) {
            return Optional.empty();
        }
        return articleRepository.getArticleBySlugAndId(slug, id);
    }

    @Override
    public Optional<Article> getArticleBySlugAndIdWithPermissionCheck(String slug, Long id, UserInfo userInfo) {
        Optional<Article> article = getArticleBySlugAndId(slug, id);

        if(article.isEmpty()){
            throw new IllegalArgumentException("Không có bài báo hợp lệ");
        }

        if(userInfo.getRoleId().getName().contains("EDITOR")){
            Optional<Editor> editor = editorService.getEditorByUserId(userInfo.getId());

            if(editor.isEmpty()){
                throw new AccessDeniedException("Không tồn tại người dùng này");
            }

            boolean isEditorOfArticle = articleRepository.isEditorOfArticle(editor.get().getId(),article.get().getId());

            if(!isEditorOfArticle){
                throw new AccessDeniedException("Không có quyền truy cập vào tài nguyên này");
            }
        }
        return article;
    }

    @Override
    public List<Article> getArticlesByCategoryId(Long categoryId) {
        if (categoryId == null) {
            return List.of();
        }
        return articleRepository.getArticlesByCategoryId(categoryId);
    }

    @Override
    public List<Article> searchArticles(Map<String, String> filters, Map<String, String> params) {
        if (filters == null || filters.isEmpty()) {
            return articleRepository.getArticles(params);
        }
        return articleRepository.searchArticles(filters, params);
    }

    @Override
    public List<Article> getArticles(Map<String, String> params) {
        return articleRepository.getArticles(params);
    }

    @Override
    public Article updateArticle(Article article) {
        if (article == null || article.getId() == null) {
            throw new IllegalArgumentException("Invalid article data");
        }
        if (!isArticleValid(article)) {
            throw new IllegalArgumentException("Invalid article data");
        }
        return articleRepository.updateArticle(article);
    }

    @Override
    public boolean deleteArticle(Long id) {
        if (id == null) {
            return false;
        }
        Optional<Article> article = articleRepository.getArticleById(id);
        if (article.isEmpty()) {
            return false; // Article not found
        }
        return articleRepository.deleteArticle(id);
    }

    @Override
    public long countArticles() {
        return articleRepository.countArticles();
    }

    @Override
    public long countSearchArticles(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return articleRepository.countArticles();
        }
        return articleRepository.countSearchArticles(filters);
    }

    @Override
    public boolean isArticleValid(Article article) {
        if (article == null) {
            return false;
        }
        if (article.getCategoryId() == null || article.getCategoryId().getId() == null) {
            return false;
        }
        if (article.getOriginalUrl() == null || article.getOriginalUrl().isEmpty()) {
            return false;
        }

        if (article.getTitle() == null || article.getTitle().isEmpty()) {
            return false;
        }
        return true;

    }
}

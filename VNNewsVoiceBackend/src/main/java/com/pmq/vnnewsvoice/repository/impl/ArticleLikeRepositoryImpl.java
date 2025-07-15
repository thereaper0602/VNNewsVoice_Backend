package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.ArticleLike;
import com.pmq.vnnewsvoice.repository.ArticleLikeRepository;

import java.util.List;

public class ArticleLikeRepositoryImpl implements ArticleLikeRepository{

    @Override
    public ArticleLike addArticleLike(ArticleLike articleLike) {
        return null;
    }

    @Override
    public List<ArticleLike> getArticleLikesByArticleId(Long articleId) {
        return List.of();
    }

    @Override
    public List<ArticleLike> getArticleLikesByUserId(Long userId) {
        return List.of();
    }

    @Override
    public boolean deleteArticleLike(Long id) {
        return false;
    }

    @Override
    public long countArticleLikesByArticleId(Long articleId) {
        return 0;
    }
}

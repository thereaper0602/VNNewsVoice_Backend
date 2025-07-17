package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.ArticleLike;

import java.util.List;

public interface ArticleLikeService {
    ArticleLike addArticleLike(ArticleLike articleLike);

    List<ArticleLike> getArticleLikesByArticleId(Long articleId);

    List<ArticleLike> getArticleLikesByUserId(Long userId);

    boolean deleteArticleLike(Long id);

    long countArticleLikesByArticleId(Long articleId);
}

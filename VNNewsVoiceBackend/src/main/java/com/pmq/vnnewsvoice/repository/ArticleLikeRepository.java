package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.ArticleLike;

import java.util.List;

public interface ArticleLikeRepository {
    ArticleLike addArticleLike(ArticleLike articleLike);

    List<ArticleLike> getArticleLikesByArticleId(Long articleId);

    List<ArticleLike> getArticleLikesByUserId(Long userId);

    boolean deleteArticleLike(Long id);

    long countArticleLikesByArticleId(Long articleId);
}

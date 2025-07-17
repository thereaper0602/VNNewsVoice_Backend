package com.pmq.vnnewsvoice.service.impl;


import com.pmq.vnnewsvoice.pojo.ArticleLike;
import com.pmq.vnnewsvoice.service.ArticleLikeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleLikeServiceImpl implements ArticleLikeService {
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

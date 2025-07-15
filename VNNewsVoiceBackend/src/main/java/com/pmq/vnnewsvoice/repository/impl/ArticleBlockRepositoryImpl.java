package com.pmq.vnnewsvoice.repository.impl;


import com.pmq.vnnewsvoice.pojo.ArticleBlock;
import com.pmq.vnnewsvoice.repository.ArticleBlockRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public class ArticleBlockRepositoryImpl implements ArticleBlockRepository {
    @Override
    public ArticleBlock addArticleBlock(ArticleBlock articleBlock) {
        return null;
    }

    @Override
    public Optional<ArticleBlock> getArticleBlockById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ArticleBlock> getArticleBlockByArticleId(Long articleId) {
        return Optional.empty();
    }

    @Override
    public Optional<ArticleBlock> getArticleBlockByArticleIdAndBlockId(Long articleId, Long blockId) {
        return Optional.empty();
    }

    @Override
    public Optional<ArticleBlock> getArticleBlockByArticleIdAndBlockType(Long articleId, String blockType) {
        return Optional.empty();
    }

    @Override
    public Optional<ArticleBlock> updateArticleBlock(ArticleBlock articleBlock) {
        return Optional.empty();
    }

    @Override
    public boolean deleteArticleBlock(Long id) {
        return false;
    }

    @Override
    public long countArticleBlocks() {
        return 0;
    }

    @Override
    public long countArticleBlocksByArticleId(Long articleId) {
        return 0;
    }

    @Override
    public long countArticleBlocksByBlockType(String blockType) {
        return 0;
    }

    @Override
    public long countArticleBlocksByArticleIdAndBlockType(Long articleId, String blockType) {
        return 0;
    }
}

package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.ArticleBlock;
import com.pmq.vnnewsvoice.service.ArticleBlockService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleBlockServiceImpl implements ArticleBlockService {
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

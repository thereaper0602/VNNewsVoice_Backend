package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.ArticleBlock;

import java.util.Optional;

public interface ArticleBlockRepository {
    ArticleBlock addArticleBlock(ArticleBlock articleBlock);

    Optional<ArticleBlock> getArticleBlockById(Long id);
    Optional<ArticleBlock> getArticleBlockByArticleId(Long articleId);
    Optional<ArticleBlock> getArticleBlockByArticleIdAndBlockId(Long articleId, Long blockId);
    Optional<ArticleBlock> getArticleBlockByArticleIdAndBlockType(Long articleId, String blockType);

    Optional<ArticleBlock> updateArticleBlock(ArticleBlock articleBlock);

    boolean deleteArticleBlock(Long id);

    long countArticleBlocks();
    long countArticleBlocksByArticleId(Long articleId);
    long countArticleBlocksByBlockType(String blockType);
    long countArticleBlocksByArticleIdAndBlockType(Long articleId, String blockType);
}

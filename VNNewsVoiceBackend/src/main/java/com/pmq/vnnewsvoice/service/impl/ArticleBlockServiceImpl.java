package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.ArticleBlock;
import com.pmq.vnnewsvoice.repository.ArticleBlockRepository;
import com.pmq.vnnewsvoice.service.ArticleBlockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleBlockServiceImpl implements ArticleBlockService {

    @Autowired
    private ArticleBlockRepository articleBlockRepository;

    @Override
    public ArticleBlock addArticleBlock(ArticleBlock articleBlock) {
        if(isValidArticleBlock(articleBlock)){
            return articleBlockRepository.addArticleBlock(articleBlock);
        }
        return null;
    }

    @Override
    public Optional<ArticleBlock> getArticleBlockById(Long id) {
        if(id == null){
            return Optional.empty();
        }
        return articleBlockRepository.getArticleBlockById(id);
    }

    @Override
    public Optional<ArticleBlock> getArticleBlockByArticleId(Long articleId) {
        if(articleId == null){
            return Optional.empty();
        }
        return articleBlockRepository.getArticleBlockByArticleId(articleId);
    }

    @Override
    public Optional<ArticleBlock> getArticleBlockByArticleIdAndBlockId(Long articleId, Long blockId) {
        if(articleId == null || blockId == null){
            return Optional.empty();
        }
        return articleBlockRepository.getArticleBlockByArticleIdAndBlockId(articleId, blockId);
    }

    @Override
    public Optional<ArticleBlock> getArticleBlockByArticleIdAndBlockType(Long articleId, String blockType) {
        if(articleId == null || blockType == null){
            return Optional.empty();
        }
        return articleBlockRepository.getArticleBlockByArticleIdAndBlockType(articleId, blockType);
    }

    @Override
    public Optional<ArticleBlock> updateArticleBlock(ArticleBlock articleBlock) {
        if(isValidArticleBlock(articleBlock)){
            return articleBlockRepository.updateArticleBlock(articleBlock);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteArticleBlock(Long id) {
        if(id == null){
            return false;
        }
        return articleBlockRepository.deleteArticleBlock(id);
    }

    @Override
    public long countArticleBlocks() {
        return articleBlockRepository.countArticleBlocks();
    }

    @Override
    public long countArticleBlocksByArticleId(Long articleId) {
        if(articleId == null){
            return 0;
        }
        return articleBlockRepository.countArticleBlocksByArticleId(articleId);
    }

    @Override
    public long countArticleBlocksByBlockType(String blockType) {
        if(blockType == null){
            return 0;
        }
        return articleBlockRepository.countArticleBlocksByBlockType(blockType);
    }

    @Override
    public long countArticleBlocksByArticleIdAndBlockType(Long articleId, String blockType) {
        if(articleId == null || blockType == null){
            return 0;
        }
        return articleBlockRepository.countArticleBlocksByArticleIdAndBlockType(articleId, blockType);
    }

    @Override
    public List<ArticleBlock> getArticleBlocksByArticleId(Long articleId) {
        if(articleId <= 0 || articleId == null){
            return List.of();
        }
        return articleBlockRepository.getArticleBlocksByArticleId(articleId);
    }

    @Override
    public boolean isValidArticleBlock(ArticleBlock articleBlock) {
        if (articleBlock == null) {
            return false;
        }
        if (articleBlock.getArticleId() == null) {
            return false;
        }
        if (articleBlock.getType() == null) {
            return false;
        }
        if("paragraph".equals(articleBlock.getType())){
            if(articleBlock.getContent() == null){
                return false;
            }
        }

        if("heading".equals(articleBlock.getType())){
            if(articleBlock.getText() == null){
                return false;
            }
        }

        if("image".equals(articleBlock.getType())){
            if(articleBlock.getAlt() == null && articleBlock.getSrc() == null){
                return false;
            }
        }
        return true;
    }
}

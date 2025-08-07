package com.pmq.vnnewsvoice.mapper;

import com.pmq.vnnewsvoice.dto.ArticleDto;
import com.pmq.vnnewsvoice.pojo.Article;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ArticleMapper implements Serializable {
    public ArticleDto toDto(Article article) {
        if (article == null) {
            return null;
        }
        
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setAuthor(article.getAuthor());
        articleDto.setPublishedDate(article.getPublishedDate());
        articleDto.setAudioUrl(article.getAudioUrl());
        articleDto.setSummary(article.getSummary());
        articleDto.setIsActive(article.getIsActive());
        articleDto.setSlug(article.getSlug());
        articleDto.setOriginalUrl(article.getOriginalUrl());
        articleDto.setTopImageUrl(article.getTopImageUrl());
        
        // Xử lý trường hợp categoryId có thể null
        if (article.getCategoryId() != null) {
            articleDto.setCategoryIdId(article.getCategoryId().getId());
            articleDto.setCategoryIdName(article.getCategoryId().getName());
        }
        
        return articleDto;
    }
}
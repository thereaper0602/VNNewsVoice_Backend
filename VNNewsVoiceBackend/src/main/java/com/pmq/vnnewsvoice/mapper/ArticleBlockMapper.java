package com.pmq.vnnewsvoice.mapper;

import com.pmq.vnnewsvoice.dto.ArticleBlockDto;
import com.pmq.vnnewsvoice.pojo.ArticleBlock;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ArticleBlockMapper implements Serializable {
    public ArticleBlockDto toDto(ArticleBlock articleBlock) {
        if (articleBlock == null) {
            return null;
        }
        
        ArticleBlockDto articleBlockDto = new ArticleBlockDto();
        articleBlockDto.setId(articleBlock.getId());
        articleBlockDto.setOrderIndex(articleBlock.getOrderIndex());
        articleBlockDto.setContent(articleBlock.getContent());
        articleBlockDto.setText(articleBlock.getText());
        articleBlockDto.setTag(articleBlock.getTag());
        articleBlockDto.setSrc(articleBlock.getSrc());
        articleBlockDto.setAlt(articleBlock.getAlt());
        articleBlockDto.setCaption(articleBlock.getCaption());
        articleBlockDto.setType(articleBlock.getType());
        
        return articleBlockDto;
    }
}

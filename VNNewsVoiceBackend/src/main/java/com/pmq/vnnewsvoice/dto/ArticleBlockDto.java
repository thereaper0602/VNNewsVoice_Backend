package com.pmq.vnnewsvoice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.pmq.vnnewsvoice.pojo.ArticleBlock}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleBlockDto implements Serializable {
    private Long id;
    private int orderIndex;
    private String type;
    private String content;
    private String text;
    private String tag;
    private String src;
    private String alt;
    private String caption;

    public ArticleBlockDto() {
    }

    public ArticleBlockDto(Long id, int orderIndex, String type, String content, String text, String tag, String src, String alt, String caption) {
        this.id = id;
        this.orderIndex = orderIndex;
        this.type = type;
        this.content = content;
        this.text = text;
        this.tag = tag;
        this.src = src;
        this.alt = alt;
        this.caption = caption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleBlockDto entity = (ArticleBlockDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.orderIndex, entity.orderIndex) &&
                Objects.equals(this.type, entity.type) &&
                Objects.equals(this.content, entity.content) &&
                Objects.equals(this.text, entity.text) &&
                Objects.equals(this.tag, entity.tag) &&
                Objects.equals(this.src, entity.src) &&
                Objects.equals(this.alt, entity.alt) &&
                Objects.equals(this.caption, entity.caption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderIndex, type, content, text, tag, src, alt, caption);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "orderIndex = " + orderIndex + ", " +
                "type = " + type + ", " +
                "content = " + content + ", " +
                "text = " + text + ", " +
                "tag = " + tag + ", " +
                "src = " + src + ", " +
                "alt = " + alt + ", " +
                "caption = " + caption + ")";
    }
}
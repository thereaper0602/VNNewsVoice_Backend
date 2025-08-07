package com.pmq.vnnewsvoice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * DTO for {@link com.pmq.vnnewsvoice.pojo.Article}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleDto implements Serializable {
    private Long id;
    private String title;
    private String author;
    private Date publishedDate;
    private String audioUrl;
    private String summary;
    private Boolean isActive;
    private String slug;
    private String originalUrl;
    private Long categoryIdId;
    private String categoryIdName;
    private String topImageUrl;

    public ArticleDto() {
    }

    public ArticleDto(Long id, String title, String author, Date publishedDate, String audioUrl, String summary, Boolean isActive, String slug, String originalUrl, Long categoryIdId, String categoryIdName, String topImageUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
        this.audioUrl = audioUrl;
        this.summary = summary;
        this.isActive = isActive;
        this.slug = slug;
        this.originalUrl = originalUrl;
        this.categoryIdId = categoryIdId;
        this.categoryIdName = categoryIdName;
        this.topImageUrl = topImageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Long getCategoryIdId() {
        return categoryIdId;
    }

    public void setCategoryIdId(Long categoryIdId) {
        this.categoryIdId = categoryIdId;
    }

    public String getCategoryIdName() {
        return categoryIdName;
    }

    public void setCategoryIdName(String categoryIdName) {
        this.categoryIdName = categoryIdName;
    }

    public String getTopImageUrl() {
        return topImageUrl;
    }

    public void setTopImageUrl(String topImageUrl) {
        this.topImageUrl = topImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleDto entity = (ArticleDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.title, entity.title) &&
                Objects.equals(this.author, entity.author) &&
                Objects.equals(this.publishedDate, entity.publishedDate) &&
                Objects.equals(this.audioUrl, entity.audioUrl) &&
                Objects.equals(this.summary, entity.summary) &&
                Objects.equals(this.isActive, entity.isActive) &&
                Objects.equals(this.slug, entity.slug) &&
                Objects.equals(this.originalUrl, entity.originalUrl) &&
                Objects.equals(this.categoryIdId, entity.categoryIdId) &&
                Objects.equals(this.categoryIdName, entity.categoryIdName) &&
                Objects.equals(this.topImageUrl, entity.topImageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, publishedDate, audioUrl, summary, isActive, slug, originalUrl, categoryIdId, categoryIdName, topImageUrl);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "title = " + title + ", " +
                "author = " + author + ", " +
                "publishedDate = " + publishedDate + ", " +
                "audioUrl = " + audioUrl + ", " +
                "summary = " + summary + ", " +
                "isActive = " + isActive + ", " +
                "slug = " + slug + ", " +
                "originalUrl = " + originalUrl + ", " +
                "categoryIdId = " + categoryIdId + ", " +
                "categoryIdName = " + categoryIdName + ", " +
                "topImageUrl = " + topImageUrl + ")";
    }
}
package com.pmq.vnnewsvoice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * DTO for {@link com.pmq.vnnewsvoice.pojo.Comment}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDto implements Serializable {
    private Long id;
    private String content;
    private Date createdAt;
    private Long articleId;
    private String articleTitle;
    private Long readerIdId;
    private String readerName;
    private Long commentReplyId;
    private Long likeCount;

    public CommentDto() {
    }

    public CommentDto(Long id, String content, Date createdAt, Long articleId, String articleTitle, Long readerIdId, String readerName, Long commentReplyId, Long likeCount) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.readerIdId = readerIdId;
        this.readerName = readerName;
        this.commentReplyId = commentReplyId;
        this.likeCount = likeCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Long getReaderIdId() {
        return readerIdId;
    }

    public void setReaderIdId(Long readerIdId) {
        this.readerIdId = readerIdId;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public Long getCommentReplyId() {
        return commentReplyId;
    }

    public void setCommentReplyId(Long commentReplyId) {
        this.commentReplyId = commentReplyId;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDto entity = (CommentDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.content, entity.content) &&
                Objects.equals(this.createdAt, entity.createdAt) &&
                Objects.equals(this.articleId, entity.articleId) &&
                Objects.equals(this.articleTitle, entity.articleTitle) &&
                Objects.equals(this.readerIdId, entity.readerIdId) &&
                Objects.equals(this.readerName, entity.readerName) &&
                Objects.equals(this.commentReplyId, entity.commentReplyId) &&
                Objects.equals(this.likeCount, entity.likeCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, createdAt, articleId, articleTitle, readerIdId, readerName, commentReplyId, likeCount);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "content = " + content + ", " +
                "createdAt = " + createdAt + ", " +
                "articleId = " + articleId + ", " +
                "articleTitle = " + articleTitle + ", " +
                "readerIdId = " + readerIdId + ", " +
                "readerName = " + readerName + ", " +
                "commentReplyId = " + commentReplyId + ", " +
                "likeCount = " + likeCount + ")";
    }
}
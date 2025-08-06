package com.pmq.vnnewsvoice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmq.vnnewsvoice.pojo.UserInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * DTO for {@link com.pmq.vnnewsvoice.pojo.Notification}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationDto implements Serializable {
    private Long id;
    private String title;
    private String content;
    private Boolean isRead;
    private Date createdAt;
    private Long userId;

    public NotificationDto() {
    }

    public NotificationDto(Long id, String title, String content, Boolean isRead, Date createdAt, Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.userId = userId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationDto entity = (NotificationDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.title, entity.title) &&
                Objects.equals(this.content, entity.content) &&
                Objects.equals(this.isRead, entity.isRead) &&
                Objects.equals(this.createdAt, entity.createdAt) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, isRead, createdAt, userId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "title = " + title + ", " +
                "content = " + content + ", " +
                "isRead = " + isRead + ", " +
                "createdAt = " + createdAt + ", " +
                "userId = " + userId + ")";
    }
}
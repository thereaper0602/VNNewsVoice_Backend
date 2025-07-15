package com.pmq.vnnewsvoice.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * DTO for {@link com.pmq.vnnewsvoice.pojo.Admin}
 */
public class AdminDto implements Serializable {
    private final Long id;
    private final Long userIdId;
    private final String userIdUsername;
    private final String userIdPassword;
    private final String userIdAvatarUrl;
    private final String userIdEmail;
    private final Date userIdCreatedAt;
    private final Date userIdBirthday;
    private final Boolean userIdIsActive;
    private final String userIdAddress;
    private final String userIdPhoneNumber;
    private final String userIdGender;

    public AdminDto(Long id, Long userIdId, String userIdUsername, String userIdPassword, String userIdAvatarUrl, String userIdEmail, Date userIdCreatedAt, Date userIdBirthday, Boolean userIdIsActive, String userIdAddress, String userIdPhoneNumber, String userIdGender) {
        this.id = id;
        this.userIdId = userIdId;
        this.userIdUsername = userIdUsername;
        this.userIdPassword = userIdPassword;
        this.userIdAvatarUrl = userIdAvatarUrl;
        this.userIdEmail = userIdEmail;
        this.userIdCreatedAt = userIdCreatedAt;
        this.userIdBirthday = userIdBirthday;
        this.userIdIsActive = userIdIsActive;
        this.userIdAddress = userIdAddress;
        this.userIdPhoneNumber = userIdPhoneNumber;
        this.userIdGender = userIdGender;
    }

    public Long getId() {
        return id;
    }

    public Long getUserIdId() {
        return userIdId;
    }

    public String getUserIdUsername() {
        return userIdUsername;
    }

    public String getUserIdPassword() {
        return userIdPassword;
    }

    public String getUserIdAvatarUrl() {
        return userIdAvatarUrl;
    }

    public String getUserIdEmail() {
        return userIdEmail;
    }

    public Date getUserIdCreatedAt() {
        return userIdCreatedAt;
    }

    public Date getUserIdBirthday() {
        return userIdBirthday;
    }

    public Boolean getUserIdIsActive() {
        return userIdIsActive;
    }

    public String getUserIdAddress() {
        return userIdAddress;
    }

    public String getUserIdPhoneNumber() {
        return userIdPhoneNumber;
    }

    public String getUserIdGender() {
        return userIdGender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminDto entity = (AdminDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.userIdId, entity.userIdId) &&
                Objects.equals(this.userIdUsername, entity.userIdUsername) &&
                Objects.equals(this.userIdPassword, entity.userIdPassword) &&
                Objects.equals(this.userIdAvatarUrl, entity.userIdAvatarUrl) &&
                Objects.equals(this.userIdEmail, entity.userIdEmail) &&
                Objects.equals(this.userIdCreatedAt, entity.userIdCreatedAt) &&
                Objects.equals(this.userIdBirthday, entity.userIdBirthday) &&
                Objects.equals(this.userIdIsActive, entity.userIdIsActive) &&
                Objects.equals(this.userIdAddress, entity.userIdAddress) &&
                Objects.equals(this.userIdPhoneNumber, entity.userIdPhoneNumber) &&
                Objects.equals(this.userIdGender, entity.userIdGender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userIdId, userIdUsername, userIdPassword, userIdAvatarUrl, userIdEmail, userIdCreatedAt, userIdBirthday, userIdIsActive, userIdAddress, userIdPhoneNumber, userIdGender);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "userIdId = " + userIdId + ", " +
                "userIdUsername = " + userIdUsername + ", " +
                "userIdPassword = " + userIdPassword + ", " +
                "userIdAvatarUrl = " + userIdAvatarUrl + ", " +
                "userIdEmail = " + userIdEmail + ", " +
                "userIdCreatedAt = " + userIdCreatedAt + ", " +
                "userIdBirthday = " + userIdBirthday + ", " +
                "userIdIsActive = " + userIdIsActive + ", " +
                "userIdAddress = " + userIdAddress + ", " +
                "userIdPhoneNumber = " + userIdPhoneNumber + ", " +
                "userIdGender = " + userIdGender + ")";
    }
}
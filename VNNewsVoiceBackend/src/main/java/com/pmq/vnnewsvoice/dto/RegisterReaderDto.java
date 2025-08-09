package com.pmq.vnnewsvoice.dto;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RegisterReaderDto {
    private Long id;
    private Long userIdId;
    private String userIdUsername;
    private String userIdPassword;
    private String userIdAvatarUrl;
    private String userIdEmail;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date userIdBirthday;
    private String userIdAddress;
    private String userIdPhoneNumber;
    private String userIdGender;
    private Long userIdRoleIdId;
    private String userIdRoleIdName;
    private String userIdAvatarPublicId;

    public RegisterReaderDto() {
    }

    public RegisterReaderDto(Long id, Long userIdId, String userIdUsername, String userIdAvatarUrl, String userIdEmail, Date userIdBirthday, String userIdAddress, String userIdPhoneNumber, String userIdGender, Long userIdRoleIdId, String userIdRoleIdName, String userIdAvatarPublicId) {
        this.id = id;
        this.userIdId = userIdId;
        this.userIdUsername = userIdUsername;
        this.userIdAvatarUrl = userIdAvatarUrl;
        this.userIdEmail = userIdEmail;
        this.userIdBirthday = userIdBirthday;
        this.userIdAddress = userIdAddress;
        this.userIdPhoneNumber = userIdPhoneNumber;
        this.userIdGender = userIdGender;
        this.userIdRoleIdId = userIdRoleIdId;
        this.userIdRoleIdName = userIdRoleIdName;
        this.userIdAvatarPublicId = userIdAvatarPublicId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserIdPassword() {
        return userIdPassword;
    }

    public void setUserIdPassword(String userIdPassword) {
        this.userIdPassword = userIdPassword;
    }


    public Long getUserIdId() {
        return userIdId;
    }

    public void setUserIdId(Long userIdId) {
        this.userIdId = userIdId;
    }

    public String getUserIdUsername() {
        return userIdUsername;
    }

    public void setUserIdUsername(String userIdUsername) {
        this.userIdUsername = userIdUsername;
    }

    public String getUserIdAvatarUrl() {
        return userIdAvatarUrl;
    }

    public void setUserIdAvatarUrl(String userIdAvatarUrl) {
        this.userIdAvatarUrl = userIdAvatarUrl;
    }

    public String getUserIdEmail() {
        return userIdEmail;
    }

    public void setUserIdEmail(String userIdEmail) {
        this.userIdEmail = userIdEmail;
    }

    public Date getUserIdBirthday() {
        return userIdBirthday;
    }

    public void setUserIdBirthday(Date userIdBirthday) {
        this.userIdBirthday = userIdBirthday;
    }

    public String getUserIdAddress() {
        return userIdAddress;
    }

    public void setUserIdAddress(String userIdAddress) {
        this.userIdAddress = userIdAddress;
    }

    public String getUserIdPhoneNumber() {
        return userIdPhoneNumber;
    }

    public void setUserIdPhoneNumber(String userIdPhoneNumber) {
        this.userIdPhoneNumber = userIdPhoneNumber;
    }

    public String getUserIdGender() {
        return userIdGender;
    }

    public void setUserIdGender(String userIdGender) {
        this.userIdGender = userIdGender;
    }

    public Long getUserIdRoleIdId() {
        return userIdRoleIdId;
    }

    public void setUserIdRoleIdId(Long userIdRoleIdId) {
        this.userIdRoleIdId = userIdRoleIdId;
    }

    public String getUserIdRoleIdName() {
        return userIdRoleIdName;
    }

    public void setUserIdRoleIdName(String userIdRoleIdName) {
        this.userIdRoleIdName = userIdRoleIdName;
    }

    public String getUserIdAvatarPublicId() {
        return userIdAvatarPublicId;
    }

    public void setUserIdAvatarPublicId(String userIdAvatarPublicId) {
        this.userIdAvatarPublicId = userIdAvatarPublicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterReaderDto entity = (RegisterReaderDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.userIdId, entity.userIdId) &&
                Objects.equals(this.userIdUsername, entity.userIdUsername) &&
                Objects.equals(this.userIdAvatarUrl, entity.userIdAvatarUrl) &&
                Objects.equals(this.userIdEmail, entity.userIdEmail) &&
                Objects.equals(this.userIdBirthday, entity.userIdBirthday) &&
                Objects.equals(this.userIdAddress, entity.userIdAddress) &&
                Objects.equals(this.userIdPhoneNumber, entity.userIdPhoneNumber) &&
                Objects.equals(this.userIdGender, entity.userIdGender) &&
                Objects.equals(this.userIdRoleIdId, entity.userIdRoleIdId) &&
                Objects.equals(this.userIdRoleIdName, entity.userIdRoleIdName) &&
                Objects.equals(this.userIdPassword, entity.userIdPassword) &&
                Objects.equals(this.userIdAvatarPublicId, entity.userIdAvatarPublicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userIdId, userIdUsername, userIdAvatarUrl, userIdEmail, userIdBirthday, userIdAddress, userIdPhoneNumber, userIdGender, userIdRoleIdId, userIdRoleIdName, userIdPassword, userIdAvatarPublicId);
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
                "userIdBirthday = " + userIdBirthday + ", " +
                "userIdAddress = " + userIdAddress + ", " +
                "userIdPhoneNumber = " + userIdPhoneNumber + ", " +
                "userIdGender = " + userIdGender + ", " +
                "userIdRoleIdId = " + userIdRoleIdId + ", " +
                "userIdRoleIdName = " + userIdRoleIdName + ", " +
                "userIdAvatarPublicId = " + userIdAvatarPublicId + ")";
    }
}

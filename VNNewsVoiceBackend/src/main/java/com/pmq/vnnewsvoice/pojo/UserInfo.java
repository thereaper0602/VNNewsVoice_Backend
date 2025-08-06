/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pmq.vnnewsvoice.pojo;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "userinfo")
@NamedQueries({
    @NamedQuery(name = "Userinfo.findAll", query = "SELECT u FROM UserInfo u"),
    @NamedQuery(name = "Userinfo.findById", query = "SELECT u FROM UserInfo u WHERE u.id = :id"),
    @NamedQuery(name = "Userinfo.findByUsername", query = "SELECT u FROM UserInfo u WHERE u.username = :username"),
    @NamedQuery(name = "Userinfo.findByPassword", query = "SELECT u FROM UserInfo u WHERE u.password = :password"),
    @NamedQuery(name = "Userinfo.findByAvatarUrl", query = "SELECT u FROM UserInfo u WHERE u.avatarUrl = :avatarUrl"),
    @NamedQuery(name = "Userinfo.findByEmail", query = "SELECT u FROM UserInfo u WHERE u.email = :email"),
    @NamedQuery(name = "Userinfo.findByCreatedAt", query = "SELECT u FROM UserInfo u WHERE u.createdAt = :createdAt"),
    @NamedQuery(name = "Userinfo.findByBirthday", query = "SELECT u FROM UserInfo u WHERE u.birthday = :birthday"),
    @NamedQuery(name = "Userinfo.findByIsActive", query = "SELECT u FROM UserInfo u WHERE u.isActive = :isActive"),
    @NamedQuery(name = "Userinfo.findByAddress", query = "SELECT u FROM UserInfo u WHERE u.address = :address"),
    @NamedQuery(name = "Userinfo.findByPhoneNumber", query = "SELECT u FROM UserInfo u WHERE u.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Userinfo.findByGender", query = "SELECT u FROM UserInfo u WHERE u.gender = :gender")})
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "address")
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "gender")
    private String gender;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userId")
    private Editor editor;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userId")
    private Reader reader;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userId")
    private Admin admin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Notification> notificationSet;
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne
    private Role roleId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<UserProvider> userProviderSet;
    @Column(name = "avatar_public_id")
    private String avatarPublicId;

    @Transient
    private MultipartFile avatarFile;

    public UserInfo() {
    }

    public UserInfo(Long id) {
        this.id = id;
    }

    public UserInfo(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public MultipartFile getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(MultipartFile avatarFile) {
        this.avatarFile = avatarFile;
    }

    public String getAvatarPublicId() {
        return avatarPublicId;
    }

    public void setAvatarPublicId(String avatarPublicId) {
        this.avatarPublicId = avatarPublicId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Set<Notification> getNotificationSet() {
        return notificationSet;
    }

    public void setNotificationSet(Set<Notification> notificationSet) {
        this.notificationSet = notificationSet;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    public Set<UserProvider> getUserproviderSet() {
        return userProviderSet;
    }

    public void setUserproviderSet(Set<UserProvider> userProviderSet) {
        this.userProviderSet = userProviderSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserInfo)) {
            return false;
        }
        UserInfo other = (UserInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pmq.vnnewsvoice.pojo.UserInfo[ id=" + id + " ]";
    }
    
}

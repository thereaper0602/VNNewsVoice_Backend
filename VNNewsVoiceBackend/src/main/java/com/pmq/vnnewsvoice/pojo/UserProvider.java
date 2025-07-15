/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pmq.vnnewsvoice.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "userprovider")
@NamedQueries({
    @NamedQuery(name = "Userprovider.findAll", query = "SELECT u FROM UserProvider u"),
    @NamedQuery(name = "Userprovider.findById", query = "SELECT u FROM UserProvider u WHERE u.id = :id"),
    @NamedQuery(name = "Userprovider.findByProviderId", query = "SELECT u FROM UserProvider u WHERE u.providerId = :providerId"),
    @NamedQuery(name = "Userprovider.findByProviderType", query = "SELECT u FROM UserProvider u WHERE u.providerType = :providerType"),
    @NamedQuery(name = "Userprovider.findByCreatedAt", query = "SELECT u FROM UserProvider u WHERE u.createdAt = :createdAt")})
public class UserProvider implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "provider_id")
    private String providerId;
    @Basic(optional = false)
    @Column(name = "provider_type")
    private String providerType;
    @Lob
    @Column(name = "provider_data", columnDefinition = "jsonb")
    private String providerData;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserInfo userId;

    public UserProvider() {
    }

    public UserProvider(Long id) {
        this.id = id;
    }

    public UserProvider(Long id, String providerId, String providerType) {
        this.id = id;
        this.providerId = providerId;
        this.providerType = providerType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getProviderData() {
        return providerData;
    }

    public void setProviderData(String providerData) {
        this.providerData = providerData;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UserInfo getUserId() {
        return userId;
    }

    public void setUserId(UserInfo userId) {
        this.userId = userId;
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
        if (!(object instanceof UserProvider)) {
            return false;
        }
        UserProvider other = (UserProvider) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pmq.vnnewsvoice.pojo.Userprovider[ id=" + id + " ]";
    }
    
}

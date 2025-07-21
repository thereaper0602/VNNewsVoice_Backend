/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pmq.vnnewsvoice.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "generator")
@NamedQueries({
    @NamedQuery(name = "Generator.findAll", query = "SELECT g FROM Generator g"),
    @NamedQuery(name = "Generator.findById", query = "SELECT g FROM Generator g WHERE g.id = :id"),
    @NamedQuery(name = "Generator.findByName", query = "SELECT g FROM Generator g WHERE g.name = :name"),
    @NamedQuery(name = "Generator.findByLogoUrl", query = "SELECT g FROM Generator g WHERE g.logoUrl = :logoUrl"),
    @NamedQuery(name = "Generator.findByUrl", query = "SELECT g FROM Generator g WHERE g.url = :url"),
    @NamedQuery(name = "Generator.findByLastTimeCrawled", query = "SELECT g FROM Generator g WHERE g.lastTimeCrawled = :lastTimeCrawled"),
    @NamedQuery(name = "Generator.findByRssUrl", query = "SELECT g FROM Generator g WHERE g.rssUrl = :rssUrl"),
    @NamedQuery(name = "Generator.findByIsActive", query = "SELECT g FROM Generator g WHERE g.isActive = :isActive"),
    @NamedQuery(name = "Generator.findByCrawlIntervalMinutes", query = "SELECT g FROM Generator g WHERE g.crawlIntervalMinutes = :crawlIntervalMinutes")})
public class Generator implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "logo_url")
    private String logoUrl;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @Column(name = "last_time_crawled")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTimeCrawled;
    @Column(name = "rss_url")
    private String rssUrl;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "crawl_interval_minutes")
    private Integer crawlIntervalMinutes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generatorId")
    private Set<Article> articleSet;

    public Generator() {
    }

    public Generator(Long id) {
        this.id = id;
    }

    public Generator(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getLastTimeCrawled() {
        return lastTimeCrawled;
    }

    public void setLastTimeCrawled(Date lastTimeCrawled) {
        this.lastTimeCrawled = lastTimeCrawled;
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getCrawlIntervalMinutes() {
        return crawlIntervalMinutes;
    }

    public void setCrawlIntervalMinutes(Integer crawlIntervalMinutes) {
        this.crawlIntervalMinutes = crawlIntervalMinutes;
    }

    public Set<Article> getArticleSet() {
        return articleSet;
    }

    public void setArticleSet(Set<Article> articleSet) {
        this.articleSet = articleSet;
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
        if (!(object instanceof Generator)) {
            return false;
        }
        Generator other = (Generator) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pmq.vnnewsvoice.pojo.Generator[ id=" + id + " ]";
    }
    
}

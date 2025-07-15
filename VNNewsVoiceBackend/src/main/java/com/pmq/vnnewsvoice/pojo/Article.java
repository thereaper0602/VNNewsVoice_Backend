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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "article")
@NamedQueries({
    @NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a"),
    @NamedQuery(name = "Article.findById", query = "SELECT a FROM Article a WHERE a.id = :id"),
    @NamedQuery(name = "Article.findByTitle", query = "SELECT a FROM Article a WHERE a.title = :title"),
    @NamedQuery(name = "Article.findByAuthor", query = "SELECT a FROM Article a WHERE a.author = :author"),
    @NamedQuery(name = "Article.findByPublishedDate", query = "SELECT a FROM Article a WHERE a.publishedDate = :publishedDate"),
    @NamedQuery(name = "Article.findByAudioUrl", query = "SELECT a FROM Article a WHERE a.audioUrl = :audioUrl"),
    @NamedQuery(name = "Article.findBySummary", query = "SELECT a FROM Article a WHERE a.summary = :summary"),
    @NamedQuery(name = "Article.findByIsActive", query = "SELECT a FROM Article a WHERE a.isActive = :isActive"),
    @NamedQuery(name = "Article.findBySlug", query = "SELECT a FROM Article a WHERE a.slug = :slug")})
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "published_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedDate;
    @Column(name = "audio_url")
    private String audioUrl;
    @Column(name = "summary")
    private String summary;
    @Column(name = "is_active")
    private Boolean isActive;
    @Basic(optional = false)
    @Column(name = "slug")
    private String slug;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne
    private Category categoryId;
    @JoinColumn(name = "editor_id", referencedColumnName = "id")
    @ManyToOne
    private Editor editorId;
    @JoinColumn(name = "generator_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Generator generatorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articleId")
    private Set<ArticleBlock> articleBlockSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articleId")
    private Set<Comment> commentSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articleId")
    private Set<ArticleLike> articleLikeSet;

    public Article() {
    }

    public Article(Long id) {
        this.id = id;
    }

    public Article(Long id, String title, String slug) {
        this.id = id;
        this.title = title;
        this.slug = slug;
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

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public Editor getEditorId() {
        return editorId;
    }

    public void setEditorId(Editor editorId) {
        this.editorId = editorId;
    }

    public Generator getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(Generator generatorId) {
        this.generatorId = generatorId;
    }

    public Set<ArticleBlock> getArticleblockSet() {
        return articleBlockSet;
    }

    public void setArticleblockSet(Set<ArticleBlock> articleBlockSet) {
        this.articleBlockSet = articleBlockSet;
    }

    public Set<Comment> getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
    }

    public Set<ArticleLike> getArticlelikeSet() {
        return articleLikeSet;
    }

    public void setArticlelikeSet(Set<ArticleLike> articleLikeSet) {
        this.articleLikeSet = articleLikeSet;
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
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pmq.vnnewsvoice.pojo.Article[ id=" + id + " ]";
    }
    
}

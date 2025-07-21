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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "articleblock")
@NamedQueries({
    @NamedQuery(name = "Articleblock.findAll", query = "SELECT a FROM ArticleBlock a"),
    @NamedQuery(name = "Articleblock.findById", query = "SELECT a FROM ArticleBlock a WHERE a.id = :id"),
    @NamedQuery(name = "Articleblock.findByOrderIndex", query = "SELECT a FROM ArticleBlock a WHERE a.orderIndex = :orderIndex"),
    @NamedQuery(name = "Articleblock.findByType", query = "SELECT a FROM ArticleBlock a WHERE a.type = :type"),
    @NamedQuery(name = "Articleblock.findByContent", query = "SELECT a FROM ArticleBlock a WHERE a.content = :content"),
    @NamedQuery(name = "Articleblock.findByText", query = "SELECT a FROM ArticleBlock a WHERE a.text = :text"),
    @NamedQuery(name = "Articleblock.findByTag", query = "SELECT a FROM ArticleBlock a WHERE a.tag = :tag"),
    @NamedQuery(name = "Articleblock.findBySrc", query = "SELECT a FROM ArticleBlock a WHERE a.src = :src"),
    @NamedQuery(name = "Articleblock.findByAlt", query = "SELECT a FROM ArticleBlock a WHERE a.alt = :alt"),
    @NamedQuery(name = "Articleblock.findByCaption", query = "SELECT a FROM ArticleBlock a WHERE a.caption = :caption")})
public class ArticleBlock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "order_index")
    private int orderIndex;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Column(name = "content")
    private String content;
    @Column(name = "text")
    private String text;
    @Column(name = "tag")
    private String tag;
    @Column(name = "src")
    private String src;
    @Column(name = "alt")
    private String alt;
    @Column(name = "caption")
    private String caption;
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    @ManyToOne
    private Article articleId;

    public ArticleBlock() {
    }

    public ArticleBlock(Long id) {
        this.id = id;
    }

    public ArticleBlock(Long id, int orderIndex, String type) {
        this.id = id;
        this.orderIndex = orderIndex;
        this.type = type;
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

    public Article getArticleId() {
        return articleId;
    }

    public void setArticleId(Article articleId) {
        this.articleId = articleId;
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
        if (!(object instanceof ArticleBlock)) {
            return false;
        }
        ArticleBlock other = (ArticleBlock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pmq.vnnewsvoice.pojo.ArticleBlock[ id=" + id + " ]";
    }
    
}

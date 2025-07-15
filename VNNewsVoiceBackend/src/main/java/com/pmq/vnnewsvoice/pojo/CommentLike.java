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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "commentlike")
@NamedQueries({
    @NamedQuery(name = "Commentlike.findAll", query = "SELECT c FROM CommentLike c"),
    @NamedQuery(name = "Commentlike.findById", query = "SELECT c FROM CommentLike c WHERE c.id = :id"),
    @NamedQuery(name = "Commentlike.findByCreatedAt", query = "SELECT c FROM CommentLike c WHERE c.createdAt = :createdAt")})
public class CommentLike implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Comment commentId;
    @JoinColumn(name = "reader_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Reader readerId;

    public CommentLike() {
    }

    public CommentLike(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Comment getCommentId() {
        return commentId;
    }

    public void setCommentId(Comment commentId) {
        this.commentId = commentId;
    }

    public Reader getReaderId() {
        return readerId;
    }

    public void setReaderId(Reader readerId) {
        this.readerId = readerId;
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
        if (!(object instanceof CommentLike)) {
            return false;
        }
        CommentLike other = (CommentLike) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pmq.vnnewsvoice.pojo.Commentlike[ id=" + id + " ]";
    }
    
}

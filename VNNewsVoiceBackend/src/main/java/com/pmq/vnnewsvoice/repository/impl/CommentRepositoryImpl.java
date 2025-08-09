package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Comment;
import com.pmq.vnnewsvoice.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class CommentRepositoryImpl implements CommentRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Comment addComment(Comment comment) {
        if (comment.getCreatedAt() == null) {
            comment.setCreatedAt(new Date());
        }
        entityManager.persist(comment);
        return comment;
    }

    @Override
    public Comment getCommentById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return entityManager.find(Comment.class, id);
    }

    @Override
    public Comment updateComment(Comment comment) {
        if (comment == null || comment.getId() == null) {
            throw new IllegalArgumentException("Comment or Comment ID cannot be null");
        }
        Comment existingComment = entityManager.find(Comment.class, comment.getId());
        if (existingComment != null) {
            return entityManager.merge(comment);
        }
        return null;
    }

    @Override
    public boolean deleteComment(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        Comment comment = entityManager.find(Comment.class, id);
        if (comment != null) {
            entityManager.remove(comment);
            return true;
        }
        return false;
    }

    @Override
    public long countCommentsByArticleId(Long articleId) {
        if (articleId == null || articleId <= 0) {
            return 0;
        }
        String hql = "SELECT COUNT(c) FROM Comment c WHERE c.articleId.id = :articleId";
        Query query = entityManager.createQuery(hql);
        query.setParameter("articleId", articleId);
        return (long) query.getSingleResult();
    }

    @Override
    public List<Comment> getCommentsByArticleId(Long articleId, Map<String, String> params) {
        if (articleId == null || articleId <= 0) {
            return List.of();
        }
        
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query = builder.createQuery(Comment.class);
        Root<Comment> root = query.from(Comment.class);
        
        Predicate articlePredicate = builder.equal(root.get("articleId").get("id"), articleId);
        query.where(articlePredicate);
        
        // Sắp xếp theo thời gian tạo mới nhất
        query.orderBy(builder.desc(root.get("createdAt")));
        
        TypedQuery<Comment> typedQuery = entityManager.createQuery(query);
        
        // Phân trang nếu có
        if (params != null) { 
            int page = Integer.parseInt(params.getOrDefault("page", "1")); 
            int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "10")); 
            // Ensure page is at least 1 to avoid negative start value 
            page = Math.max(1, page); 
            int start = (page - 1) * pageSize; 
            typedQuery.setFirstResult(start); 
            typedQuery.setMaxResults(pageSize); 
        }
        
        return typedQuery.getResultList();
    }

    @Override
    public List<Comment> getCommentsByUserId(Long userId, Map<String, String> params) {
        if (userId == null || userId <= 0) {
            return List.of();
        }
        
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query = builder.createQuery(Comment.class);
        Root<Comment> root = query.from(Comment.class);
        
        Predicate userPredicate = builder.equal(root.get("readerId").get("userId").get("id"), userId);
        query.where(userPredicate);
        
        // Sắp xếp theo thời gian tạo mới nhất
        query.orderBy(builder.desc(root.get("createdAt")));
        
        TypedQuery<Comment> typedQuery = entityManager.createQuery(query);
        
        // Phân trang nếu có
        if (params != null) { 
            int page = Integer.parseInt(params.getOrDefault("page", "1")); 
            int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "10")); 
            // Ensure page is at least 1 to avoid negative start value 
            page = Math.max(1, page); 
            int start = (page - 1) * pageSize; 
            typedQuery.setFirstResult(start); 
            typedQuery.setMaxResults(pageSize); 
        }
        
        return typedQuery.getResultList();
    }

    @Override
    public List<Comment> searchComments(Map<String, String> filters, Map<String, String> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query = builder.createQuery(Comment.class);
        Root<Comment> root = query.from(Comment.class);
        
        List<Predicate> predicates = new ArrayList<>();
        
        if (filters != null && !filters.isEmpty()) {
            // Lọc theo nội dung bình luận
            if (filters.containsKey("content")) {
                predicates.add(builder.like(
                    builder.lower(root.get("content")), 
                    "%" + filters.get("content").toLowerCase() + "%"
                ));
            }
            
            // Lọc theo ID bài viết
            if (filters.containsKey("articleId")) {
                try {
                    Long articleId = Long.parseLong(filters.get("articleId"));
                    predicates.add(builder.equal(root.get("articleId").get("id"), articleId));
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu không phải số
                }
            }
            
            // Lọc theo ID người đọc
            if (filters.containsKey("readerId")) {
                try {
                    Long readerId = Long.parseLong(filters.get("readerId"));
                    predicates.add(builder.equal(root.get("readerId").get("id"), readerId));
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu không phải số
                }
            }
        }
        
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }
        
        // Sắp xếp theo thời gian tạo mới nhất
        query.orderBy(builder.desc(root.get("createdAt")));
        
        TypedQuery<Comment> typedQuery = entityManager.createQuery(query);
        
        // Phân trang nếu có
        if (params != null) { 
            int page = Integer.parseInt(params.getOrDefault("page", "1")); 
            int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "10")); 
            // Ensure page is at least 1 to avoid negative start value 
            page = Math.max(1, page); 
            int start = (page - 1) * pageSize; 
            typedQuery.setFirstResult(start); 
            typedQuery.setMaxResults(pageSize); 
        }
        
        return typedQuery.getResultList();
    }
}

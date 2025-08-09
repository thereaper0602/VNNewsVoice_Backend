package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.Comment;
import com.pmq.vnnewsvoice.repository.CommentRepository;
import com.pmq.vnnewsvoice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment addComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }
        
        // Đảm bảo ngày tạo được thiết lập
        if (comment.getCreatedAt() == null) {
            comment.setCreatedAt(new Date());
        }
        
        return commentRepository.addComment(comment);
    }

    @Override
    public Comment getCommentById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return commentRepository.getCommentById(id);
    }

    @Override
    public Comment updateComment(Comment comment) {
        if (comment == null || comment.getId() == null) {
            throw new IllegalArgumentException("Comment or Comment ID cannot be null");
        }
        return commentRepository.updateComment(comment);
    }

    @Override
    public boolean deleteComment(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        return commentRepository.deleteComment(id);
    }

    @Override
    public long countCommentsByArticleId(Long articleId) {
        if (articleId == null || articleId <= 0) {
            return 0;
        }
        return commentRepository.countCommentsByArticleId(articleId);
    }

    @Override
    public List<Comment> getCommentsByArticleId(Long articleId, Map<String, String> params) {
        if (articleId == null || articleId <= 0) {
            return List.of();
        }
        return commentRepository.getCommentsByArticleId(articleId, params);
    }

    @Override
    public List<Comment> getCommentsByUserId(Long userId, Map<String, String> params) {
        if (userId == null || userId <= 0) {
            return List.of();
        }
        return commentRepository.getCommentsByUserId(userId, params);
    }

    @Override
    public List<Comment> searchComments(Map<String, String> filters, Map<String, String> params) {
        return commentRepository.searchComments(filters, params);
    }
}

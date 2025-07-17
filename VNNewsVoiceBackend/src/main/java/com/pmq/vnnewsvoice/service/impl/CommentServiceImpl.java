package com.pmq.vnnewsvoice.service.impl;


import com.pmq.vnnewsvoice.pojo.Comment;
import com.pmq.vnnewsvoice.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public Comment addComment(Comment comment) {
        return null;
    }

    @Override
    public Comment getCommentById(Long id) {
        return null;
    }

    @Override
    public Comment updateComment(Comment comment) {
        return null;
    }

    @Override
    public boolean deleteComment(Long id) {
        return false;
    }

    @Override
    public long countCommentsByArticleId(Long articleId) {
        return 0;
    }

    @Override
    public List<Comment> getCommentsByArticleId(Long articleId, Map<String, String> params) {
        return List.of();
    }

    @Override
    public List<Comment> getCommentsByUserId(Long userId, Map<String, String> params) {
        return List.of();
    }

    @Override
    public List<Comment> searchComments(Map<String, String> filters, Map<String, String> params) {
        return List.of();
    }
}

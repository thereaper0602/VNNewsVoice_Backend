package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.Comment;

import java.util.List;
import java.util.Map;

public interface CommentRepository {
    Comment addComment(Comment comment);

    Comment getCommentById(Long id);
    Comment updateComment(Comment comment);
    boolean deleteComment(Long id);

    long countCommentsByArticleId(Long articleId);

    List<Comment> getCommentsByArticleId(Long articleId, Map<String, String> params);
    List<Comment> getCommentsByUserId(Long userId, Map<String, String> params);
    List<Comment> searchComments(Map<String, String> filters, Map<String, String> params);
}

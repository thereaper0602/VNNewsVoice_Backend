package com.pmq.vnnewsvoice.mapper;

import com.pmq.vnnewsvoice.dto.CommentDto;
import com.pmq.vnnewsvoice.pojo.Comment;
import com.pmq.vnnewsvoice.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CommentMapper implements Serializable {
    
    @Autowired
    private CommentLikeService commentLikeService;
    
    public CommentDto toDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreatedAt(comment.getCreatedAt());
        
        // Xử lý trường hợp articleId có thể null
        if (comment.getArticleId() != null) {
            commentDto.setArticleId(comment.getArticleId().getId());
            commentDto.setArticleTitle(comment.getArticleId().getTitle());
        }
        
        // Xử lý trường hợp readerId có thể null
        if (comment.getReaderId() != null && comment.getReaderId().getUserId() != null) {
            commentDto.setReaderIdId(comment.getReaderId().getId());
            commentDto.setReaderName(comment.getReaderId().getUserId().getUsername());
        }
        
        // Xử lý trường hợp commentReplyId có thể null
        if (comment.getCommentReplyId() != null) {
            commentDto.setCommentReplyId(comment.getCommentReplyId().getId());
        }
        
        // Đếm số lượng like cho comment
        long likeCount = commentLikeService.countCommentLikesByCommentId(comment.getId());
        commentDto.setLikeCount(likeCount);
        
        return commentDto;
    }
}
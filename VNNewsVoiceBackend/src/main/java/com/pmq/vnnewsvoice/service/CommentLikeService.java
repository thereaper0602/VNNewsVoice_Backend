package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.CommentLike;

public interface CommentLikeService {
    CommentLike addCommentLike(CommentLike commentLike);

    long countCommentLikesByCommentId(Long commentId);

    boolean deleteCommentLike(Long id);
}

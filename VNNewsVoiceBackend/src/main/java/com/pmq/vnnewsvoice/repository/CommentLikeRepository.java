package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.CommentLike;

public interface CommentLikeRepository {
    CommentLike addCommentLike(CommentLike commentLike);

    long countCommentLikesByCommentId(Long commentId);
}

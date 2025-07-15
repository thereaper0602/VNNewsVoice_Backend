package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.CommentLike;
import com.pmq.vnnewsvoice.repository.CommentLikeRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class CommentLikeRepositoryImpl implements CommentLikeRepository {
    @Override
    public CommentLike addCommentLike(CommentLike commentLike) {
        return null;
    }

    @Override
    public long countCommentLikesByCommentId(Long commentId) {
        return 0;
    }
}

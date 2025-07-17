package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.CommentLike;
import com.pmq.vnnewsvoice.service.CommentLikeService;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {
    @Override
    public CommentLike addCommentLike(CommentLike commentLike) {
        return null;
    }

    @Override
    public long countCommentLikesByCommentId(Long commentId) {
        return 0;
    }

    @Override
    public boolean deleteCommentLike(Long id) {
        return false;
    }
}

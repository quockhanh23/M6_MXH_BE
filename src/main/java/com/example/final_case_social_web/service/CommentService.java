package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.Comment;
import org.springframework.data.repository.query.Param;

public interface CommentService extends GeneralService<Comment> {
    Iterable<Comment> getCommentByIdUser(@Param("id") Long id);

    Iterable<Comment> getCommentByIdPost(@Param("id") Long id);
}

package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.Comment;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentService extends GeneralService<Comment> {
    List<Comment> getCommentByIdUser(@Param("id") Long id);

    List<Comment> getCommentByIdPost(@Param("id") Long id);
    List<Comment> getCommentTrue();
}

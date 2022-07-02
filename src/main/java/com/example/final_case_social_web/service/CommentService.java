package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.Comment;

public interface CommentService extends IGeneralService<Comment> {

    Iterable<Comment> getCommentByIdUser(Long id);

    Iterable<Comment> getCommentByIdPost(Long id);
}

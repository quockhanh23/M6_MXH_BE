package com.example.final_case_social_web.service.impl;

import com.example.final_case_social_web.model.Comment;
import com.example.final_case_social_web.repository.CommentRepository;
import com.example.final_case_social_web.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Iterable<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void remove(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Iterable<Comment> getCommentByIdUser(Long id) {
        return commentRepository.getCommentByIdUser(id);
    }

    @Override
    public Iterable<Comment> getCommentByIdPost(Long id) {
        return commentRepository.getCommentByIdPost(id);
    }
}

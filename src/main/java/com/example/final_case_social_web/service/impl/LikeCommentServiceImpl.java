package com.example.final_case_social_web.service.impl;

import com.example.final_case_social_web.model.LikeComment;
import com.example.final_case_social_web.repository.LikeCommentRepository;
import com.example.final_case_social_web.service.LikeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeCommentServiceImpl implements LikeCommentService {
    @Autowired
    private LikeCommentRepository likeCommentRepository;

    @Override
    public Iterable<LikeComment> findAll() {
        return likeCommentRepository.findAll();
    }

    @Override
    public Optional<LikeComment> findById(Long id) {
        return likeCommentRepository.findById(id);
    }

    @Override
    public LikeComment save(LikeComment likeComment) {
        return likeCommentRepository.save(likeComment);
    }

    @Override
    public List<LikeComment> findLikeComment(Long idPost, Long idUser) {
        return likeCommentRepository.findLikeComment(idPost, idUser);
    }

    @Override
    public List<LikeComment> findAllLikeCommentByPostId(Long idPost) {
        return likeCommentRepository.findAllLikeCommentByPostId(idPost);
    }

    @Override
    public void delete(LikeComment entity) {
        likeCommentRepository.delete(entity);
    }
}

package com.example.final_case_social_web.service.impl;

import com.example.final_case_social_web.model.DisLikeComment;
import com.example.final_case_social_web.repository.DisLikeCommentRepository;
import com.example.final_case_social_web.service.DisLikeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisLikeCommentImpl implements DisLikeCommentService {

    @Autowired
    private DisLikeCommentRepository disLikeCommentRepository;

    @Override
    public Optional<DisLikeComment> findById(Long id) {
        return disLikeCommentRepository.findById(id);
    }

    @Override
    public List<DisLikeComment> findDisLikeComment(Long idComment, Long idUser) {
        return disLikeCommentRepository.findDisLikeComment(idComment, idUser);
    }

    @Override
    public List<DisLikeComment> findAllDisLikeCommentByComment(Long idComment) {
        return disLikeCommentRepository.findAllDisLikeCommentByComment(idComment);
    }

    @Override
    public void delete(DisLikeComment entity) {
        disLikeCommentRepository.delete(entity);
    }

    @Override
    public DisLikeComment save(DisLikeComment disLikeComment) {
        return disLikeCommentRepository.save(disLikeComment);
    }
}

package com.example.final_case_social_web.service.impl;

import com.example.final_case_social_web.model.DisLikePost;
import com.example.final_case_social_web.repository.DisLikePostRepository;
import com.example.final_case_social_web.service.DisLikePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DislikePostServiceImpl implements DisLikePostService {
    @Autowired
    private DisLikePostRepository disLikeRepository;

    @Override
    public Iterable<DisLikePost> findAll() {
        return disLikeRepository.findAll();
    }

    @Override
    public Optional<DisLikePost> findById(Long id) {
        return disLikeRepository.findById(id);
    }

    @Override
    public DisLikePost save(DisLikePost disLikePost) {
        return disLikeRepository.save(disLikePost);
    }

    @Override
    public List<DisLikePost> findDisLike(Long idPost, Long idUser) {
        return disLikeRepository.findDisLike(idPost, idUser);
    }

    @Override
    public List<DisLikePost> findAllDisLikeByPostId(Long idPost) {
        return disLikeRepository.findAllDisLikeByPostId(idPost);
    }
}

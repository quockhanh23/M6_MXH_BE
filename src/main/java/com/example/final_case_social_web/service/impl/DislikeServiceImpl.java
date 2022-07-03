package com.example.final_case_social_web.service.impl;

import com.example.final_case_social_web.model.DisLike;
import com.example.final_case_social_web.repository.DisLikeRepository;
import com.example.final_case_social_web.service.DisLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DislikeServiceImpl implements DisLikeService {
    @Autowired
    private DisLikeRepository disLikeRepository;

    @Override
    public Iterable<DisLike> findAll() {
        return disLikeRepository.findAll();
    }

    @Override
    public Optional<DisLike> findById(Long id) {
        return disLikeRepository.findById(id);
    }

    @Override
    public DisLike save(DisLike disLike) {
        return disLikeRepository.save(disLike);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<DisLike> findDisLike(Long idPost, Long idUser) {
        return disLikeRepository.findDisLike(idPost, idUser);
    }

    @Override
    public List<DisLike> findAllDisLikeByPostId(Long idPost) {
        return disLikeRepository.findAllDisLikeByPostId(idPost);
    }
}

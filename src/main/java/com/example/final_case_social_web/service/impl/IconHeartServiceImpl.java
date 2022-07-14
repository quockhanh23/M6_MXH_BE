package com.example.final_case_social_web.service.impl;

import com.example.final_case_social_web.model.IconHeart;
import com.example.final_case_social_web.repository.IconHeartRepository;
import com.example.final_case_social_web.service.IconHeartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IconHeartServiceImpl implements IconHeartService {

    @Autowired
    private IconHeartRepository iconHeartRepository;

    @Override
    public Iterable<IconHeart> findAll() {
        return iconHeartRepository.findAll();
    }

    @Override
    public Optional<IconHeart> findById(Long id) {
        return iconHeartRepository.findById(id);
    }

    @Override
    public IconHeart save(IconHeart iconHeart) {
        return iconHeartRepository.save(iconHeart);
    }


    @Override
    public void delete(IconHeart entity) {
        iconHeartRepository.delete(entity);
    }

    @Override
    public List<IconHeart> findHeart(Long idPost, Long idUser) {
        return iconHeartRepository.findHeart(idPost, idUser);
    }

    @Override
    public List<IconHeart> findAllHeartByPostId(Long idPost) {
        return iconHeartRepository.findAllHeartByPostId(idPost);
    }
}

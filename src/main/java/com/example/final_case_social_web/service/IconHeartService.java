package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.IconHeart;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IconHeartService extends GeneralService<IconHeart> {
    void delete(IconHeart entity);

    List<IconHeart> findHeart (@Param("idPost")Long idPost, @Param("idUser") Long idUser);

    List<IconHeart> findAllHeartByPostId (@Param("idPost")Long idPost);
}

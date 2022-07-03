package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.DisLike;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisLikeService extends IGeneralService<DisLike> {
    List<DisLike> findDisLike (@Param("idPost")Long idPost, @Param("idUser") Long idUser);
}
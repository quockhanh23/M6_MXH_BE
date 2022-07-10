package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.DisLikePost;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisLikePostService extends GeneralService<DisLikePost> {
    List<DisLikePost> findDisLike (@Param("idPost")Long idPost, @Param("idUser") Long idUser);
    List<DisLikePost> findAllDisLikeByPostId (@Param("idPost")Long idPost);
}

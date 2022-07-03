package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.LikePost;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikePostService extends IGeneralService<LikePost> {
    List<LikePost> findLike (@Param("idPost")Long idPost, @Param("idUser") Long idUser);
}

package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.LikeComment;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeCommentService extends GeneralService<LikeComment> {
    List<LikeComment> findLikeComment(@Param("idComment") Long idComment, @Param("idUser") Long idUser);

    List<LikeComment> findAllLikeCommentByPostId(@Param("idComment") Long idComment);

    void delete(LikeComment entity);
}

package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.DisLikeComment;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DisLikeCommentService {
    Optional<DisLikeComment> findById(Long id);

    List<DisLikeComment> findDisLikeComment(@Param("idComment") Long idComment, @Param("idUser") Long idUser);

    List<DisLikeComment> findAllDisLikeCommentByComment(@Param("idComment") Long idComment);

    void delete(DisLikeComment entity);

    DisLikeComment save(DisLikeComment disLikeComment);
}

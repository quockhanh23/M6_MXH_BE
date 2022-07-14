package com.example.final_case_social_web.repository;

import com.example.final_case_social_web.model.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

    @Modifying
    @Query(value = "select * from like_comment where comment_id = :idComment and user_id = :idUser", nativeQuery = true)
    List<LikeComment> findLikeComment (@Param("idComment")Long idPost, @Param("idUser") Long idUser);

    @Modifying
    @Query(value = "select * from like_comment where comment_id = :idComment and user_id is not null", nativeQuery = true)
    List<LikeComment> findAllLikeCommentByPostId (@Param("idComment")Long idComment);
}

package com.example.final_case_social_web.repository;

import com.example.final_case_social_web.model.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    @Modifying
    @Query(value = "select * from like_post where post_id = :idPost and user_id = :idUser", nativeQuery = true)
    List<LikePost> findLike (@Param("idPost")Long idPost, @Param("idUser") Long idUser);

    @Modifying
    @Query(value = "select * from like_post where post_id = :idPost and user_id is not null", nativeQuery = true)
    List<LikePost> findAllLikeByPostId (@Param("idPost")Long idPost);
}

package com.example.final_case_social_web.repository;

import com.example.final_case_social_web.model.DisLikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisLikePostRepository extends JpaRepository<DisLikePost, Long> {
    @Modifying
    @Query(value = "select * from dis_like where post_id = :idPost and user_id = :idUser", nativeQuery = true)
    List<DisLikePost> findDisLike (@Param("idPost")Long idPost, @Param("idUser") Long idUser);

    @Modifying
    @Query(value = "select * from dis_like where post_id = :idPost and user_id is not null", nativeQuery = true)
    List<DisLikePost> findAllDisLikeByPostId (@Param("idPost")Long idPost);

    void delete(DisLikePost entity);
}

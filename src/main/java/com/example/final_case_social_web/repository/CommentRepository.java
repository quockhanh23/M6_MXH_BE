package com.example.final_case_social_web.repository;

import com.example.final_case_social_web.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Query(value = "select * from comment where user_id = :id", nativeQuery = true)
    List<Comment> getCommentByIdUser(@Param("id") Long id);

    @Modifying
    @Query(value = "select * from comment where post_id = :id and is_delete = false", nativeQuery = true)
    List<Comment> getCommentByIdPost(@Param("id") Long id);
}

package com.example.final_case_social_web.repository;

import com.example.final_case_social_web.model.Post2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post2,Long> {
    Iterable<Post2> findByStatusContaining (String status);
    List<Post2> findAllByContentContaining (String content);

    @Modifying
    @Query(value = "select * from post2 where user_id = :id and is_delete = false order by create_at desc", nativeQuery = true)
    List<Post2> findAllPostByUser (@Param("id")Long id);

    @Modifying
    @Query(value = "select * from post2 where is_delete = false and status like 'Public' order by create_at desc", nativeQuery = true)
    List<Post2> AllPost ();
}

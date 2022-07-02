package com.example.final_case_social_web.repository;

import com.example.final_case_social_web.model.Post2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post2,Long> {
    Iterable<Post2> findByStatusContaining (String status);

    @Modifying
    @Query(value = "select id_friend from friend_relation where id_user = :id1 and status = '3'", nativeQuery = true)
    Iterable<Post2> findAllPost ();
}

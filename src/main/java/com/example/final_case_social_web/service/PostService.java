package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.Post2;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostService extends GeneralService<Post2> {
    List<Post2> findAllPostByUser(@Param("id") Long id);

    List<Post2> allPost();

    Optional<Post2> checkPostPublic(Long id);

    Optional<Post2> checkPostPrivate(Long id);

    Optional<Post2> checkPostDelete(Long id);

    void delete(Post2 entity);
}

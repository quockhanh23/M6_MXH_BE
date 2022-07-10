package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.Post2;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostService extends GeneralService<Post2> {
    List<Post2> findAllPostByUser (@Param("id")Long id);

    List<Post2> allPost ();
}

package com.example.final_case_social_web.repository;

import com.example.final_case_social_web.model.Post2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusPostRepository extends JpaRepository<Post2, Long> {
}

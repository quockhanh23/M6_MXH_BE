package com.example.final_case_social_web.repository;

import com.example.final_case_social_web.model.ShortNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortNewsRepository extends JpaRepository<ShortNews, Long> {
    void delete(ShortNews entity);
}

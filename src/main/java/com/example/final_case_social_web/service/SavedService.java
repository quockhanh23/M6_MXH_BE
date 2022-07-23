package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.Saved;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SavedService extends GeneralService<Saved> {
    List<Saved> findAllSavedPost(@Param("idUser") Long idUser);
}

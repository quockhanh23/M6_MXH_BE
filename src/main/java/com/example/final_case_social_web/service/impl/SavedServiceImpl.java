package com.example.final_case_social_web.service.impl;

import com.example.final_case_social_web.model.Saved;
import com.example.final_case_social_web.repository.SavedRepository;
import com.example.final_case_social_web.service.SavedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SavedServiceImpl implements SavedService {

    @Autowired
    private SavedRepository savedRepository;

    @Override
    public Iterable<Saved> findAll() {
        return savedRepository.findAll();
    }

    @Override
    public Optional<Saved> findById(Long id) {
        return savedRepository.findById(id);
    }

    @Override
    public Saved save(Saved saved) {
        return savedRepository.save(saved);
    }
}

package com.example.final_case_social_web.service.impl;

import com.example.final_case_social_web.model.ShortNews;
import com.example.final_case_social_web.repository.ShortNewsRepository;
import com.example.final_case_social_web.service.ShortNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShortNewsServiceImpl implements ShortNewsService {
    @Autowired
    private ShortNewsRepository shortNewsRepository;

    @Override
    public Iterable<ShortNews> findAll() {
        return shortNewsRepository.findAll();
    }

    @Override
    public Optional<ShortNews> findById(Long id) {
        return shortNewsRepository.findById(id);
    }

    @Override
    public ShortNews save(ShortNews shortNews) {
        return shortNewsRepository.save(shortNews);
    }

}

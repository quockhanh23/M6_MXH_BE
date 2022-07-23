package com.example.final_case_social_web.service.impl;

import com.example.final_case_social_web.model.TheGroup;
import com.example.final_case_social_web.repository.GroupRepository;
import com.example.final_case_social_web.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Iterable<TheGroup> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<TheGroup> findById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public TheGroup save(TheGroup theGroup) {
        return groupRepository.save(theGroup);
    }
}

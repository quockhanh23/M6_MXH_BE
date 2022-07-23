package com.example.final_case_social_web.service.impl;

import com.example.final_case_social_web.model.GroupParticipant;
import com.example.final_case_social_web.repository.GroupParticipantRepository;
import com.example.final_case_social_web.service.GroupParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupParticipantServiceImpl implements GroupParticipantService {
    @Autowired
    private GroupParticipantRepository groupParticipantRepository;

    @Override
    public Iterable<GroupParticipant> findAll() {
        return groupParticipantRepository.findAll();
    }

    @Override
    public Optional<GroupParticipant> findById(Long id) {
        return groupParticipantRepository.findById(id);
    }

    @Override
    public GroupParticipant save(GroupParticipant groupParticipant) {
        return groupParticipantRepository.save(groupParticipant);
    }
}

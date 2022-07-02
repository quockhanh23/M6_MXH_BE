package com.example.final_case_social_web.service;


import com.example.final_case_social_web.model.Role;

public interface RoleService {
    Iterable<Role> findAll();


    void save(Role role);

    Role findByName(String name);
}

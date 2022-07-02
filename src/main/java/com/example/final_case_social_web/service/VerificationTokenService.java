package com.example.final_case_social_web.service;


import com.example.final_case_social_web.model.VerificationToken;

public interface VerificationTokenService {
    VerificationToken findByToken(String token);

    void save(VerificationToken token);
}
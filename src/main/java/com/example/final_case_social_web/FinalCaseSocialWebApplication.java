package com.example.final_case_social_web;

import com.example.final_case_social_web.service.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class FinalCaseSocialWebApplication implements CommandLineRunner {
    @Resource
    FilesStorageService storageService;
    public static void main(String[] args) {
        SpringApplication.run(FinalCaseSocialWebApplication.class, args);
    }

    public void run(String... arg) throws Exception {
        storageService.deleteAll();
        storageService.init();
    }
}

package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.model.ShortNews;
import com.example.final_case_social_web.service.ShortNewsService;
import com.example.final_case_social_web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/api/news")
@Slf4j
public class ShortNewsRestController {
    @Autowired
    private ShortNewsService shortNewsService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<ShortNews>> allShortNews() {
        Iterable<ShortNews> shortNews = shortNewsService.findAll();
        return new ResponseEntity<>(shortNews, HttpStatus.OK);
    }
}

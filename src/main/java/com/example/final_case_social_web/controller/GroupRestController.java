package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.service.GroupService;
import com.example.final_case_social_web.service.PostService;
import com.example.final_case_social_web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/groups")
@Slf4j
public class GroupRestController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;
}

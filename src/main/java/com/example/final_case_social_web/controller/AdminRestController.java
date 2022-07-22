package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.dto.UserDTO;
import com.example.final_case_social_web.model.User;
import com.example.final_case_social_web.service.CommentService;
import com.example.final_case_social_web.service.PostService;
import com.example.final_case_social_web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/api/admins")
@Slf4j
public class AdminRestController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    ModelMapper mapper;

    @GetMapping("/getAllUser")
    public ResponseEntity<Iterable<UserDTO>> getAllUser() {
        List<UserDTO> list = new ArrayList<>();
        Iterable<User> users = userService.findAllRoleUser();
        if (!users.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<User> userList = new ArrayList<>();
        userList = (List<User>) users;

        if (userList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (int i = 0; i < userList.size(); i++) {
            UserDTO userDto = mapper.map(userList.get(i), UserDTO.class);
            list.add(userDto);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}

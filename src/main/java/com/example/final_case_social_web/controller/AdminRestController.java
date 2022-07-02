package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.model.Comment;
import com.example.final_case_social_web.model.User;
import com.example.final_case_social_web.service.CommentService;
import com.example.final_case_social_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/api/admins")
public class AdminRestController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    public ResponseEntity<Iterable<User>> getAllUser() {
        Iterable<User> users = userService.findAllRoleUser();
        if (!users.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<User> userList = new ArrayList<>();
        userList = (List<User>) users;
        if (userList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getAllCommentByIdUser")
    public ResponseEntity<Iterable<Comment>> getAllCommentByIdUser(@RequestParam Long id) {
        Iterable<Comment> commentIterable = commentService.getCommentByIdUser(id);
        if (!commentIterable.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentIterable, HttpStatus.OK);
    }

    @GetMapping("/getAllCommentByIdPost")
    public ResponseEntity<Iterable<Comment>> getAllCommentByIdPost(@RequestParam Long id) {
        Iterable<Comment> commentIterable = commentService.getCommentByIdPost(id);
        if (!commentIterable.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentIterable, HttpStatus.OK);
    }
}

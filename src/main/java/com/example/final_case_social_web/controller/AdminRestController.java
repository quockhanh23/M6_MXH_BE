package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.common.Constants;
import com.example.final_case_social_web.dto.UserDTO;
import com.example.final_case_social_web.model.Post2;
import com.example.final_case_social_web.model.User;
import com.example.final_case_social_web.service.PostService;
import com.example.final_case_social_web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/api/admins")
@Slf4j
public class AdminRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    ModelMapper modelMapper;

    // Xem tất cả user
    @GetMapping("/getAllUser")
    public ResponseEntity<Iterable<UserDTO>> getAllUser() {
        List<UserDTO> userDTOList = new ArrayList<>();
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
            UserDTO userDto = modelMapper.map(userList.get(i), UserDTO.class);
            userDTOList.add(userDto);
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    // Ban user
    @DeleteMapping("/changeStatusUserBan")
    public ResponseEntity<String> changeStatusUserBan(@RequestParam Long idAdmin, @RequestParam Long idUser) {

        Optional<User> userOptional = userService.findById(idAdmin);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<User> optionalUser = userService.findById(idUser);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (userOptional.get().getRoles().toString().substring(17, 27).equals("ROLE_ADMIN")) {
            userOptional.get().setStatus(Constants.statusUser2);
            userService.save(userOptional.get());
            return new ResponseEntity<>(userOptional.get().getStatus(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    // Xoá post trong database
    @DeleteMapping("/deletePost")
    public ResponseEntity<Post2> deletePost(@RequestParam Long idAdmin, @RequestParam Long idPost) {

        Optional<User> adminOptional = userService.findById(idAdmin);
        if (!adminOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (adminOptional.get().getRoles().toString().substring(17, 27).equals("ROLE_ADMIN")) {
            postService.delete(postOptional.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}

package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.common.Constants;
import com.example.final_case_social_web.model.Post2;
import com.example.final_case_social_web.model.Saved;
import com.example.final_case_social_web.model.User;
import com.example.final_case_social_web.service.PostService;
import com.example.final_case_social_web.service.SavedService;
import com.example.final_case_social_web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/api/saves")
@Slf4j
public class SavedRestController {
    @Autowired
    private SavedService savedService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("/savePost")
    public ResponseEntity<Saved> savePost(@RequestParam Long idPost,
                                          @RequestParam Long idUser) {

        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Saved saved = new Saved();
        saved.setSaveDate(new Date());
        saved.setStatus(Constants.statusSaved1);
        saved.setIdUser(idUser);
        saved.setPost2(postOptional.get());
        savedService.save(saved);

        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping("/removeSavePost")
    public ResponseEntity<Saved> removeSavePost(@RequestParam Long idUser, @RequestParam Long idSaved) {

        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Saved> savedOptional = savedService.findById(idSaved);
        if (!savedOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        savedOptional.get().setStatus(Constants.statusSaved2);
        savedService.save(savedOptional.get());
        return new ResponseEntity<>(savedOptional.get(), HttpStatus.OK);
    }
}

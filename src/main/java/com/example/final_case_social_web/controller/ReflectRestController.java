package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.model.*;
import com.example.final_case_social_web.service.DisLikeService;
import com.example.final_case_social_web.service.PostService;
import com.example.final_case_social_web.service.LikePostService;
import com.example.final_case_social_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/api/refs")
public class ReflectRestController {
    @Autowired
    private LikePostService likePostService;
    @Autowired
    private DisLikeService disLikeService;
    @Autowired
    private UserService userService;
    @Autowired
    PostService postService;


    @GetMapping("/getAllLike")
    public ResponseEntity<Iterable<LikePost>> getAllLike(@RequestParam Long idPost) {
        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<LikePost> likePosts = likePostService.findAll();
        if (!likePosts.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(likePosts, HttpStatus.OK);
    }

    @GetMapping("/getAllDisLike")
    public ResponseEntity<Iterable<DisLike>> getAllDisLike(@RequestParam Long idPost) {
        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<DisLike> disLikes = disLikeService.findAll();
        if (!disLikes.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(disLikes, HttpStatus.OK);
    }

    @PostMapping("/createLike")
    public ResponseEntity<LikePost> createLike(@RequestBody LikePost likePost, @RequestParam Long idPost) {
        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<DisLike> disLikes = disLikeService.findAll();
        for (DisLike disLike : disLikes) {
            if (disLike.getUserDisLike().equals(likePost.getUserLike())) {
                disLike.setUserDisLike(null);
                disLikeService.save(disLike);
            }
        }
        likePost.setPost(postOptional.get());
        likePostService.save(likePost);
        return new ResponseEntity<>(likePost, HttpStatus.OK);
    }

    @PostMapping("/createDisLike")
    public ResponseEntity<DisLike> createDisLike(@RequestBody DisLike disLike, @RequestParam Long idPost) {
        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<LikePost> likePosts = likePostService.findAll();
        for (LikePost likePost : likePosts) {
            if (likePost.getUserLike().equals(disLike.getUserDisLike())) {
                likePost.setUserLike(null);
                likePostService.save(likePost);
            }
        }
        disLike.setPost(postOptional.get());
        disLikeService.save(disLike);
        return new ResponseEntity<>(disLike, HttpStatus.OK);
    }
}

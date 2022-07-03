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

import java.util.Date;
import java.util.List;
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

    // Xem like
    @GetMapping("/getAllLike")
    public ResponseEntity<List<LikePost>> getAllLike(@RequestParam Long idPost) {
        if (idPost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<LikePost> likePosts = likePostService.findAllLikeByPostId(idPost);
        if (likePosts.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(likePosts, HttpStatus.OK);
    }

    // Xem dislike
    @GetMapping("/getAllDisLike")
    public ResponseEntity<Iterable<DisLike>> getAllDisLike(@RequestParam Long idPost) {
        if (idPost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<DisLike> disLikes = disLikeService.findAllDisLikeByPostId(idPost);
        if (disLikes.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(disLikes, HttpStatus.OK);
    }

    // Tạo like
    @PostMapping("/createLike")
    public ResponseEntity<LikePost> createLike(@RequestBody LikePost likePost,
                                               @RequestParam Long idPost,
                                               @RequestParam Long idUser) {
        List<LikePost> likePostIterable = likePostService.findLike(idPost, idUser);
        if (likePostIterable.size() == 1) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<DisLike> disLikes = disLikeService.findAll();
        for (DisLike disLike : disLikes) {
            if (disLike.getUserDisLike().getId().equals(idUser)) {
                disLike.setUserDisLike(null);
                disLikeService.save(disLike);
            }
        }
        likePost.setUserLike(userOptional.get());
        likePost.setCreateAt(new Date());
        likePost.setPost(postOptional.get());
        likePostService.save(likePost);
        return new ResponseEntity<>(likePost, HttpStatus.OK);
    }

    // Tạo dislike
    @PostMapping("/createDisLike")
    public ResponseEntity<DisLike> createDisLike(@RequestBody DisLike disLike,
                                                 @RequestParam Long idPost,
                                                 @RequestParam Long idUser) {
        List<DisLike> disLikes = disLikeService.findDisLike(idPost, idUser);
        if (disLikes.size() == 1) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<LikePost> likePosts = likePostService.findAll();
        for (LikePost likePost : likePosts) {
            if (likePost.getUserLike().getId().equals(idUser)) {
                likePost.setUserLike(null);
                likePostService.save(likePost);
            }
        }
        disLike.setUserDisLike(userOptional.get());
        disLike.setCreateAt(new Date());
        disLike.setPost(postOptional.get());
        disLikeService.save(disLike);
        return new ResponseEntity<>(disLike, HttpStatus.OK);
    }
}

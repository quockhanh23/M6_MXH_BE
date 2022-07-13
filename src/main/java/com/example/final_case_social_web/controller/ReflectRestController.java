package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.model.DisLikePost;
import com.example.final_case_social_web.model.LikePost;
import com.example.final_case_social_web.model.Post2;
import com.example.final_case_social_web.model.User;
import com.example.final_case_social_web.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/api/refs")
@Slf4j
public class ReflectRestController {
    @Autowired
    private LikePostService likePostService;
    @Autowired
    private DisLikePostService disLikePostService;
    @Autowired
    private UserService userService;
    @Autowired
    PostService postService;

    @Autowired
    LikeCommentService likeCommentService;

    @Autowired
    DisLikeCommentService disLikeCommentService;

    // Xem like của post
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

    // Xem dislike của post
    @GetMapping("/getAllDisLike")
    public ResponseEntity<Iterable<DisLikePost>> getAllDisLike(@RequestParam Long idPost) {
        if (idPost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<DisLikePost> disLikePosts = disLikePostService.findAllDisLikeByPostId(idPost);
        if (disLikePosts.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(disLikePosts, HttpStatus.OK);
    }

    // Tạo like nếu đã like sẽ unlike
    @PostMapping("/createLike")
    public ResponseEntity<LikePost> createLike(@RequestBody LikePost likePost,
                                               @RequestParam Long idPost,
                                               @RequestParam Long idUser) {
        List<LikePost> likePostIterable = likePostService.findLike(idPost, idUser);
        if (likePostIterable.size() == 1) {
            likePostService.delete(likePostIterable.get(0));
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

        likePost.setUserLike(userOptional.get());
        likePost.setCreateAt(LocalDateTime.now());
        likePost.setPost(postOptional.get());
        likePostService.save(likePost);
        return new ResponseEntity<>(likePost, HttpStatus.OK);
    }

    // Tạo dislike nếu đã dislike sẽ undislike
    @PostMapping("/createDisLike")
    public ResponseEntity<DisLikePost> createDisLike(@RequestBody DisLikePost disLikePost,
                                                     @RequestParam Long idPost,
                                                     @RequestParam Long idUser) {
        List<DisLikePost> disLikePosts = disLikePostService.findDisLike(idPost, idUser);
        if (disLikePosts.size() == 1) {
            disLikePosts.get(0).setUserDisLike(null);
            disLikePostService.save(disLikePosts.get(0));
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

        disLikePost.setUserDisLike(userOptional.get());
        disLikePost.setCreateAt(new Date());
        disLikePost.setPost(postOptional.get());
        disLikePostService.save(disLikePost);
        return new ResponseEntity<>(disLikePost, HttpStatus.OK);
    }
}

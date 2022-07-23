package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.model.*;
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
    @Autowired
    IconHeartService iconHeartService;
    @Autowired
    CommentService commentService;

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

    // Tạo, xóa like
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

    // Tạo, xóa dislike
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

    // Tạo, xóa heart
    @PostMapping("/createHeart")
    public ResponseEntity<IconHeart> createHeart(@RequestBody IconHeart iconHeart,
                                                 @RequestParam Long idPost,
                                                 @RequestParam Long idUser) {
        List<IconHeart> iconHearts = iconHeartService.findHeart(idPost, idUser);
        if (iconHearts.size() == 1) {
            iconHeartService.delete(iconHearts.get(0));
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

        iconHeart.setUser(userOptional.get());
        iconHeart.setCreateAt(new Date());
        iconHeart.setPost(postOptional.get());
        iconHeartService.save(iconHeart);
        return new ResponseEntity<>(iconHeart, HttpStatus.OK);
    }

    // Tạo, xóa like comment
    @PostMapping("/createLikeComment")
    public ResponseEntity<LikeComment> createLikeComment(@RequestBody LikeComment likeComment,
                                                         @RequestParam Long idComment,
                                                         @RequestParam Long idUser) {
        List<LikeComment> likeComments = likeCommentService.findLikeComment(idComment, idUser);
        if (likeComments.size() == 1) {
            likeCommentService.delete(likeComments.get(0));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Comment> commentOptional = commentService.findById(idComment);
        if (!commentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        likeComment.setUserLike(userOptional.get());
        likeComment.setCreateAt(new Date());
        likeComment.setComment(commentOptional.get());
        likeCommentService.save(likeComment);
        return new ResponseEntity<>(likeComment, HttpStatus.OK);
    }

    // Tạo, xóa dislike comment
    @PostMapping("/createDisLikeComment")
    public ResponseEntity<DisLikeComment> createDisLikeComment(@RequestBody DisLikeComment disLikeComment,
                                                               @RequestParam Long idComment,
                                                               @RequestParam Long idUser) {
        List<DisLikeComment> disLikeComments = disLikeCommentService.findDisLikeComment(idComment, idUser);
        if (disLikeComments.size() == 1) {
            disLikeCommentService.delete(disLikeComments.get(0));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Comment> commentOptional = commentService.findById(idComment);
        if (!commentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        disLikeComment.setUserDisLike(userOptional.get());
        disLikeComment.setCreateAt(new Date());
        disLikeComment.setComment(commentOptional.get());
        disLikeCommentService.save(disLikeComment);
        return new ResponseEntity<>(disLikeComment, HttpStatus.OK);
    }
}

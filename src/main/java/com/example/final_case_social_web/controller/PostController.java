package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.common.Constants;
import com.example.final_case_social_web.common.LogMessage;
import com.example.final_case_social_web.model.LikePost;
import com.example.final_case_social_web.model.Post2;
import com.example.final_case_social_web.model.User;
import com.example.final_case_social_web.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/posts")
@Slf4j
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    LikePostService likePostService;

    @Autowired
    DisLikePostService disLikePostService;

    @Autowired
    CommentService commentService;

    @GetMapping("")
    public ResponseEntity<Iterable<Post2>> findAll() {
        Iterable<Post2> postIterable = postService.findAll();
        return new ResponseEntity<>(postIterable, HttpStatus.OK);
    }

    @GetMapping("/allPostPublic")
    public ResponseEntity<List<Post2>> allPostPublic() {
        List<Post2> post2List = postService.allPost();
        for (int i = 0; i < post2List.size(); i++) {
            List<LikePost> likePost = likePostService.findAllLikeByPostId(post2List.get(i).getId());
            post2List.get(i).setNumberLike(likePost.size());
            postService.save(post2List.get(i));
        }

        return new ResponseEntity<>(post2List, HttpStatus.OK);
    }

    @GetMapping("/findAllPostByUser")
    public ResponseEntity<List<Post2>> findAllPostByUser(@RequestParam Long id) {
        List<Post2> post2List = postService.findAllPostByUser(id);
        return new ResponseEntity<>(post2List, HttpStatus.OK);
    }

    @PostMapping("/createPost")
    public ResponseEntity<Post2> createPost(@RequestBody Post2 post, @RequestParam Long idUser) {
        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            log.warn(LogMessage.logMessage4);
            log.warn("Không tìm thấy User");
            log.warn(LogMessage.logMessage4);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        post.setStatus(Constants.statusPost1);
        post.setEditAt(null);
        post.setDelete(false);
        post.setUser(userOptional.get());
        post.setCreateAt(new Date());
        if (post.getContent().equals("") || post.getContent() == null) {
            return new ResponseEntity<>(post, HttpStatus.NOT_FOUND);
        }
        postService.save(post);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/updatePost")
    public ResponseEntity<Post2> update(@RequestParam Long idPost,
                                        @RequestParam Long idUser,
                                        @RequestBody Post2 post) {
        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postOptional.get().setEditAt(new Date());
        postOptional.get().setUser(userOptional.get());
        if (post.getContent() != null) {
            postOptional.get().setContent(post.getContent());
        }
        postService.save(postOptional.get());
        return new ResponseEntity<>(postOptional.get(), HttpStatus.OK);
    }

    @DeleteMapping("/changeStatusPublic")
    public ResponseEntity<Post2> changeStatusPublic(@RequestParam Long idPost,
                                                    @RequestParam Long idUser) {
        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postOptional.get().setStatus(Constants.statusPost1);
        postService.save(postOptional.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/changeStatusPrivate")
    public ResponseEntity<Post2> changeStatus(@RequestParam Long idPost,
                                              @RequestParam Long idUser) {
        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postOptional.get().setStatus(Constants.statusPost2);
        postService.save(postOptional.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/changeStatusDelete")
    public ResponseEntity<Post2> delete(@RequestParam Long idPost,
                                        @RequestParam Long idUser) {
        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postOptional.get().setStatus(Constants.statusPost3);
        postOptional.get().setDelete(true);
        postService.save(postOptional.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post2> findOne(@PathVariable Long id) {
        Optional<Post2> postOptional = postService.findById(id);
        return postOptional.map(post -> new ResponseEntity<>(post, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

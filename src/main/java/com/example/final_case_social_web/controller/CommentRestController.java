package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.model.Comment;
import com.example.final_case_social_web.model.Post2;
import com.example.final_case_social_web.model.User;
import com.example.final_case_social_web.service.CommentService;
import com.example.final_case_social_web.service.PostService;
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
@RequestMapping("/api/comments")
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;


    @GetMapping("/all")
    public ResponseEntity<Iterable<Comment>> allComment() {
        Iterable<Comment> commentList = commentService.findAll();
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @GetMapping("/allComment")
    public ResponseEntity<List<Comment>> allCommentById(@RequestParam Long id) {
        List<Comment> commentList = commentService.getCommentByIdPost(id);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @PostMapping("/createComment")
    public ResponseEntity<Comment> creatComment(@RequestBody Comment comment,
                                                @RequestParam Long idUser,
                                                @RequestParam Long idPost) {
        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Post2> post2Optional = postService.findById(idPost);
        if (!post2Optional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (comment.getContent() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        comment.setCreateAt(new Date());
        comment.setDelete(false);
        comment.setPost(post2Optional.get());
        comment.setEditAt(null);
        comment.setUser(userOptional.get());
        commentService.save(comment);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/deleteComment")
    public ResponseEntity<Comment> deleteComment(@RequestParam Long idUser,
                                                 @RequestParam Long idComment,
                                                 @RequestParam Long idPost) {
        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Comment> commentOptional = commentService.findById(idComment);
        if (!commentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Post2> post2Optional = postService.findById(idPost);
        if (!post2Optional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if ((userOptional.get().getId().equals(commentOptional.get().getUser().getId())) ||
                (userOptional.get().getId().equals(commentOptional.get().getPost().getUser().getId()))) {
            commentOptional.get().setDeleteAt(new Date());
            commentOptional.get().setDelete(true);
            commentService.save(commentOptional.get());
            return new ResponseEntity<>(commentOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

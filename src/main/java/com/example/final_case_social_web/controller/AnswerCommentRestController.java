package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.model.AnswerComment;
import com.example.final_case_social_web.model.Comment;
import com.example.final_case_social_web.model.User;
import com.example.final_case_social_web.service.AnswerCommentService;
import com.example.final_case_social_web.service.CommentService;
import com.example.final_case_social_web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/api/AnswerComments")
@Slf4j
public class AnswerCommentRestController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private AnswerCommentService answerCommentService;

    @GetMapping("/allAnswerComment")
    public ResponseEntity<Iterable<AnswerComment>> allAnswerComment() {
        Iterable<AnswerComment> list = answerCommentService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/createAnswerComment")
    public ResponseEntity<AnswerComment> createAnswerComment(@RequestBody AnswerComment answerComment,
                                                             @RequestParam Long idUser,
                                                             @RequestParam Long idComment) {
        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Comment> commentOptional = commentService.findById(idComment);
        if (!commentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (answerComment.getContent() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        answerComment.setCreateAt(LocalDateTime.now());
        answerComment.setDelete(false);
        answerComment.setComment(commentOptional.get());
        answerComment.setEditAt(null);
        answerComment.setUser(userOptional.get());
        answerCommentService.save(answerComment);
        return new ResponseEntity<>(answerComment, HttpStatus.OK);
    }

}
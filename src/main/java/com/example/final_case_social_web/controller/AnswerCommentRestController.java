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

    // Tạo mới AnswerComment
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
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        answerComment.setCreateAt(LocalDateTime.now());
        answerComment.setDelete(false);
        answerComment.setComment(commentOptional.get());
        answerComment.setEditAt(null);
        answerComment.setUser(userOptional.get());
        answerCommentService.save(answerComment);
        return new ResponseEntity<>(answerComment, HttpStatus.OK);
    }

    // Xóa AnswerComment (đổi trạng thái delete = true)
    @DeleteMapping("/deleteAnswerComment")
    public ResponseEntity<AnswerComment> deleteAnswerComment(@RequestParam Long idUser,
                                                             @RequestParam Long idComment,
                                                             @RequestParam Long idAnswerComment) {
        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Comment> commentOptional = commentService.findById(idComment);
        if (!commentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<AnswerComment> answerComment = answerCommentService.findById(idAnswerComment);
        if (!answerComment.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if ((userOptional.get().getId().equals(answerComment.get().getUser().getId())) ||
                (userOptional.get().getId().equals(answerComment.get().getComment().getUser().getId()))) {
            answerComment.get().setDeleteAt(LocalDateTime.now());
            answerComment.get().setDelete(true);
            answerCommentService.save(answerComment.get());
            return new ResponseEntity<>(answerComment.get(), HttpStatus.OK);
        }
        if ((userOptional.get().getId().equals(answerComment.get().getComment().getPost().getUser().getId()))) {
            answerComment.get().setDeleteAt(LocalDateTime.now());
            answerComment.get().setDelete(true);
            answerCommentService.save(answerComment.get());
            return new ResponseEntity<>(answerComment.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}

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
import java.util.List;
import java.util.Optional;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/api/comments")
@Slf4j
public class CommentRestController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private LikeCommentService likeCommentService;
    @Autowired
    private DisLikeCommentService disLikeCommentService;

    // Danh sách tất cả comment và kiểm tra số lượng like, dislike
    @GetMapping("/all")
    public ResponseEntity<Iterable<Comment>> allComment() {
        List<Comment> list = commentService.allComment();
        for (Comment comment : list) {
            List<LikeComment> likeComments = likeCommentService.findAllLikeCommentByPostId(comment.getId());
            comment.setNumberLike((long) likeComments.size());
            List<DisLikeComment> disLikeComments = disLikeCommentService.findAllDisLikeCommentByComment(comment.getId());
            comment.setNumberDisLike((long) disLikeComments.size());
            commentService.save(comment);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Danh sách comment theo id post
    @GetMapping("/allComment")
    public ResponseEntity<List<Comment>> allCommentById(@RequestParam Long id) {
        List<Comment> commentList = commentService.getCommentByIdPost(id);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    // Tạo mới comment
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
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        comment.setCreateAt(LocalDateTime.now());
        comment.setDelete(false);
        comment.setPost(post2Optional.get());
        comment.setEditAt(null);
        comment.setUser(userOptional.get());
        commentService.save(comment);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    // Xóa comment
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
            commentOptional.get().setDeleteAt(LocalDateTime.now());
            commentOptional.get().setDelete(true);
            commentService.save(commentOptional.get());
            return new ResponseEntity<>(commentOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/getAllCommentByIdUser")
    public ResponseEntity<Iterable<Comment>> getAllCommentByIdUser(@RequestParam Long id) {
        Iterable<Comment> commentIterable = commentService.getCommentByIdUser(id);
        if (!commentIterable.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentIterable, HttpStatus.OK);
    }

    @GetMapping("/getAllCommentByIdPost")
    public ResponseEntity<Iterable<Comment>> getAllCommentByIdPost(@RequestParam Long id) {
        Iterable<Comment> commentIterable = commentService.getCommentByIdPost(id);
        if (!commentIterable.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentIterable, HttpStatus.OK);
    }
}

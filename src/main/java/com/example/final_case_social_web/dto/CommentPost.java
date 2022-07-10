package com.example.final_case_social_web.dto;

import com.example.final_case_social_web.model.Comment;
import com.example.final_case_social_web.model.Post2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPost {
    private Post2 post2;
    private Comment comment;
}

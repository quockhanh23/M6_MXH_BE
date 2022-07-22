package com.example.final_case_social_web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private Date createAt;
    private Date editAt;
    private String content;
    private Long numberLike;
    private Long numberDisLike;
    private String image;
    private Long iconHeart;
    private UserDTO userDTO;
}

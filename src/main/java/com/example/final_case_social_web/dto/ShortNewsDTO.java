package com.example.final_case_social_web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortNewsDTO {
    private Long id;
    private String content;
    private Date createAt;
    private int expired;
    private int remaining;
    private String image;
    private UserDTO userDTO;
}

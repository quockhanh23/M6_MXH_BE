package com.example.final_case_social_web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavedDTO {
    private Long id;
    private Long idUser;
    private String status;
    private Date saveDate;
    private PostDTO postDTO;
}

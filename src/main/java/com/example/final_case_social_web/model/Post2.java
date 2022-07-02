package com.example.final_case_social_web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Post2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createAt;
    private Date editAt;
    private String content;
    private String status;
    private boolean isDelete;
    private String like;
    private String disLike;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}


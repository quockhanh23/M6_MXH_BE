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
    @Column(nullable = false,  columnDefinition = "VARCHAR(1000)")
    private String content;
    private String status;
    private boolean isDelete;
    private int numberLike;
    private int numberDisLike;
    private String image;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}


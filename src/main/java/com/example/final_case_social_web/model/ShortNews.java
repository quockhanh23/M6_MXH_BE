package com.example.final_case_social_web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Date createAt;
    private int expired;
    private int remaining;
    private Date toDay;
    @Column(length = 1000)
    private String image;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

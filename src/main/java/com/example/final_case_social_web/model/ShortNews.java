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
    private String name;
    private Date createAt;
    private Date expired;
    @Column(length = 1000)
    private String image;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

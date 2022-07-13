package com.example.final_case_social_web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AnswerComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 500, nullable = false)
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime editAt;
    private boolean isDelete;
    private LocalDateTime deleteAt;
    @Column(length = 1000)
    private String image;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

package com.example.final_case_social_web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String linkImage;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Image(Long id, String linkImage, User user) {
        this.id = id;
        this.linkImage = linkImage;
        this.user = user;
    }
}

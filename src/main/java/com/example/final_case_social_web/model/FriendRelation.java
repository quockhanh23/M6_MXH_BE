package com.example.final_case_social_web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "friendRelation")
public class FriendRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUser;
    private Long idFriend;
    private String statusFriend;
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;

    @ManyToOne
    @JoinColumn(name = "userLogin_id")
    private User userLogin;

    public FriendRelation(Long id, Long idUser, Long idFriend, String statusFriend) {
        this.id = id;
        this.idUser = idUser;
        this.idFriend = idFriend;
        this.statusFriend = statusFriend;
    }

    public FriendRelation(Long idUser, Long idFriend, String statusFriend) {
        this.idUser = idUser;
        this.idFriend = idFriend;
        this.statusFriend = statusFriend;
    }
}

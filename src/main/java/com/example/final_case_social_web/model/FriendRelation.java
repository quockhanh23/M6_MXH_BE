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
    private String status;

    public FriendRelation(Long id, Long idUser, Long idFriend, String status) {
        this.id = id;
        this.idUser = idUser;
        this.idFriend = idFriend;
        this.status = status;
    }

    public FriendRelation(Long idUser, Long idFriend, String status) {
        this.idUser = idUser;
        this.idFriend = idFriend;
        this.status = status;
    }
}

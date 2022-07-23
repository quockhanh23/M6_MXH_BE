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
public class TheGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupName;
    private Date createAt;
    private String createBy;
    private String status;
    private String type;
    private Long numberUser;
    @Column(length = 1000)
    private String avatarGroup;
    @Column(length = 1000)
    private String coverGroup;
    private Long idUserCreate;
}

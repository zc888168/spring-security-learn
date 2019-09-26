package org.spring.security.learn.chapter4.entity;


import lombok.Data;

import javax.persistence.*;

/**
 * @author .
 */
@Data
@Entity
@Table(name = "t_user")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

}

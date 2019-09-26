package org.spring.security.learn.chapter4.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author .
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {

    private Long id;

    private String username;

    private String password;

}

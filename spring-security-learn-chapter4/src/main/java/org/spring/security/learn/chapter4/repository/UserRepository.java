package org.spring.security.learn.chapter4.repository;


import org.spring.security.learn.chapter4.entity.Account;

/**
 * @author .
 */
public interface UserRepository {

    /**
     * findByUsername
     *
     * @param username
     * @return
     */
    Account findByUsername(String username);
}

package org.spring.security.learn.chapter4.repository;


import org.spring.security.learn.chapter4.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author .
 */
public interface UserRepository extends JpaRepository<MyUser, Long> {

    /**
     * findByUsername
     * @param username
     * @return
     */
    MyUser findByUsername(String username);
}

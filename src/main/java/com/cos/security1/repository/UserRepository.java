package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD 함수를 JpaRepository가 들고 있음
//Repository 어노테이션 없이도 Ioc 가능
public interface UserRepository extends JpaRepository<User,Integer> {
    // findBy규칙 -> Username 문법
    // select * from user where username = 파라미터;
    public User findByUsername(String username);
}

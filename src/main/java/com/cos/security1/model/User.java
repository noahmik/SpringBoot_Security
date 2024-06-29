package com.cos.security1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.sql.Timestamp;
@Entity //JPA 엔티티 정의
@Data //getter,setter 자동 제공
public class User {
    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increament
    private int id;
    private String username;
    private String password;
    private String email;
    private String role; //user,admin,manger
    private Timestamp createDate;

    private String provider;
    private String providerId;
}

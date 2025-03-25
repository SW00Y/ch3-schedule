package com.example.schedule.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private String email;
    private Timestamp add_log;

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.add_log = Timestamp.valueOf(LocalDateTime.now());
    }

}
package com.example.springbasiclayered.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private Long user_id;
    private String name;
    private String content;
    private String pwd;
    private Timestamp add_log;
    private Timestamp upp_log;

    public Schedule(Long user_id,String name, String content, String pwd) {
        this.user_id = user_id;
        this.content = content;
        this.name = name;
        this.pwd = pwd;
        this.add_log = Timestamp.valueOf(LocalDateTime.now());
        this.upp_log = Timestamp.valueOf(LocalDateTime.now());
    }

    public void update(String content) {
        this.content = content;
    }

}

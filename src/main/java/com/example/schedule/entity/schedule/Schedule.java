package com.example.schedule.entity.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    
    //add_log, upp_log 가 이미 현재시간을 가지고 생성
    public Schedule(Long user_id, String name, String content, String pwd) {
        this.user_id = user_id;
        this.content = content;
        this.name = name;
        this.pwd = pwd;
        this.add_log = Timestamp.valueOf(LocalDateTime.now());
        this.upp_log = Timestamp.valueOf(LocalDateTime.now());
    }

}
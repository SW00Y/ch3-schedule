package com.example.schedule.dto;

import lombok.Getter;

import java.sql.Timestamp;


@Getter
public class ScheduleRequestDto {

    private Long user_id;
    private String name;
    private String content;
    private String pwd;
    private Timestamp add_log;
    private Timestamp upp_log;


}

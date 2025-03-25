package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class ScheduleResponseDto {

    private Long id;
    private Long user_id;
    private String name;
    private String content;
    @JsonIgnore
    private String pwd;
    private Timestamp add_log;
    private Timestamp upp_log;


    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.user_id = schedule.getUser_id();
        this.name = schedule.getName();
        this.content = schedule.getContent();
        this.pwd = schedule.getPwd();
        this.add_log = schedule.getAdd_log();
        this.upp_log = schedule.getAdd_log();
    }
}

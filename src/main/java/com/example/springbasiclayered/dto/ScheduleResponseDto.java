package com.example.springbasiclayered.dto;

import com.example.springbasiclayered.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class ScheduleResponseDto {

    private Long id;
    private Long user_id;
    private String content;
    private String pwd;
    private Timestamp add_log;
    private Timestamp upp_log;


    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.content = schedule.getContent();
        this.pwd = schedule.getPwd();
        this.add_log = schedule.getAdd_log();
        this.upp_log = schedule.getAdd_log();
    }
}

package com.example.springbasiclayered.dto;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Memo 요청 DTO
 */
@Getter
public class ScheduleRequestDto {

    private Long user_id;
    private String content;
    private String pwd;
    private Timestamp add_log;
    private Timestamp upp_log;


}

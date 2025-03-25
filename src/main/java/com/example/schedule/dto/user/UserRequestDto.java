package com.example.schedule.dto.user;

import lombok.Getter;

import java.sql.Timestamp;


@Getter
public class UserRequestDto {
    private Long id;
    private String name;
    private String email;
    private Timestamp add_log;
}

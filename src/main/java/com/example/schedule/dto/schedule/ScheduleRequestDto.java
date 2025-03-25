package com.example.schedule.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.sql.Timestamp;


@Getter
public class ScheduleRequestDto {
    private Long user_id;
    private String name;

    @NotBlank(message = "REQUEST_DTO_ERROR_CONTENT_NULL")
    @Size(max = 200, message = "REQUEST_DTO_ERROR_SIZE_200")
    private String content;

    @NotBlank(message = "REQUEST_DTO_ERROR_PWD_NULL")
    private String pwd;

    private Timestamp add_log;
    private Timestamp upp_log;
}

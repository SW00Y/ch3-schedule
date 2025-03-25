package com.example.schedule.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.sql.Timestamp;


@Getter
public class ScheduleRequestDto {
    private Long user_id;
    private String name;

    @NotBlank(message = "내용은 필수값입니다.")
    @Size(max = 200, message = "내용은 200자 이내로 작성해야합니다.")
    private String content;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    private String pwd;

    private Timestamp add_log;
    private Timestamp upp_log;
}

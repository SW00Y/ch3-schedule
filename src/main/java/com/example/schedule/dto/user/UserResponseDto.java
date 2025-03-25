package com.example.schedule.dto.user;

import com.example.schedule.entity.schedule.Schedule;
import com.example.schedule.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private Timestamp add_log;


    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.add_log = user.getAdd_log();
    }
}

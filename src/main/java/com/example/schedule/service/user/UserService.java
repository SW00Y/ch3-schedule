package com.example.schedule.service.user;

import com.example.schedule.dto.schedule.ScheduleRequestDto;
import com.example.schedule.dto.schedule.ScheduleResponseDto;
import com.example.schedule.dto.user.UserRequestDto;
import com.example.schedule.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto saveUser(UserRequestDto userRequestDto);

    UserResponseDto findUserById(Long id);

    UserResponseDto updateUser(Long id, String name, String email);

    void deleteUser(Long id, String email);

}

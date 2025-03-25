package com.example.schedule.service.user;

import com.example.schedule.dto.schedule.ScheduleRequestDto;
import com.example.schedule.dto.schedule.ScheduleResponseDto;
import com.example.schedule.dto.user.UserRequestDto;
import com.example.schedule.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {

    //유저 생성용
    UserResponseDto saveUser(UserRequestDto userRequestDto);

    //유저 검색
    UserResponseDto findUserById(Long id);

    //유저 수정
    UserResponseDto updateUser(Long id, String name, String email);

    //유저 삭제
    void deleteUser(Long id, String email);

}

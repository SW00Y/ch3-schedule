package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

    List<ScheduleResponseDto> findAllSchedule();

    ScheduleResponseDto findScheduleById(Long id);

    List<ScheduleResponseDto> findScheduleByUserId(Long id);

    ScheduleResponseDto updateSchedule(Long id, String content, String pwd);

    void deleteSchedule(Long id, String pwd);

}

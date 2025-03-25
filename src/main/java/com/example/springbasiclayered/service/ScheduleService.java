package com.example.springbasiclayered.service;

import com.example.springbasiclayered.dto.ScheduleRequestDto;
import com.example.springbasiclayered.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

    List<ScheduleResponseDto> findAllSchedule();

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateSchedule(Long id, String content);

    void deleteSchedule(Long id);

}

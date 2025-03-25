package com.example.schedule.service.schedule;

import com.example.schedule.dto.schedule.ScheduleRequestDto;
import com.example.schedule.dto.schedule.ScheduleResponseDto;
import com.example.schedule.entity.schedule.Schedule;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

    List<ScheduleResponseDto> findAllSchedule();

    ScheduleResponseDto findScheduleById(Long id);

    List<ScheduleResponseDto> findScheduleByUserId(Long id);

    ScheduleResponseDto updateSchedule(Long id,ScheduleRequestDto requestDto);

    void deleteSchedule(Long id, String pwd);

    List<ScheduleResponseDto> findSchedulesPage(int pageNum, int pageSize);

}

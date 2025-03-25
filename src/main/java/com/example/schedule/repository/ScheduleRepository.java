package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    Optional<Schedule> findScheduleById(Long id);

    List<ScheduleResponseDto> findScheduleByUserId(Long id);

    Optional<String> findUserPwd(Long id, String password);

    int updateSchedule(Long id, String content, String pwd);

    int deleteSchedule(Long id, String pwd);


}

package com.example.schedule.repository.schedule;

import com.example.schedule.dto.schedule.ScheduleRequestDto;
import com.example.schedule.dto.schedule.ScheduleResponseDto;
import com.example.schedule.entity.schedule.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    Optional<Schedule> findScheduleById(Long id);

    List<ScheduleResponseDto> findScheduleByUserId(Long id);

    Optional<String> findUserPwd(Long id, String password);

    Optional<String> findUserName(Long userId);

    int updateSchedule(Long id, String content);

    int deleteSchedule(Long id);

    List<ScheduleResponseDto> findSchedulesPage(int pageNum, int pageSize);

}

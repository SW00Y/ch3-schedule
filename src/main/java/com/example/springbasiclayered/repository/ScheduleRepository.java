package com.example.springbasiclayered.repository;

import com.example.springbasiclayered.dto.ScheduleResponseDto;
import com.example.springbasiclayered.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    Optional<Schedule> findScheduleById(Long id);

    Schedule findScheduleByIdOrElseThrow(Long id);

    int updateSchedule(Long id, String content);

    int deleteSchedule(Long id);


}

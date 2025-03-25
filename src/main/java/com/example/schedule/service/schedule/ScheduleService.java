package com.example.schedule.service.schedule;

import com.example.schedule.dto.schedule.ScheduleRequestDto;
import com.example.schedule.dto.schedule.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    //일정 생성
    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

    //전체 일정 조회
    List<ScheduleResponseDto> findAllSchedule();

    //ScheduleId로 일정 조회
    ScheduleResponseDto findScheduleById(Long id);

    //userId로 일정 조회
    List<ScheduleResponseDto> findScheduleByUserId(Long id);

    //Schedule 수정
    ScheduleResponseDto updateSchedule(Long id,ScheduleRequestDto requestDto);

    //Schedule 삭제
    void deleteSchedule(Long id, String pwd);

    //Schedule Pagination
    List<ScheduleResponseDto> findSchedulesPage(int pageNum, int pageSize);

}

package com.example.springbasiclayered.service;

import com.example.springbasiclayered.dto.ScheduleRequestDto;
import com.example.springbasiclayered.dto.ScheduleResponseDto;
import com.example.springbasiclayered.entity.Schedule;
import com.example.springbasiclayered.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(
                requestDto.getUser_id(),
                requestDto.getName(),
                requestDto.getContent(),
                requestDto.getPwd()
        );
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule() {
        List<ScheduleResponseDto> allSchedule = scheduleRepository.findAllSchedules();
        return allSchedule;
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findScheduleByUserId(Long id) {
        List<ScheduleResponseDto> allUserIdSchedule = scheduleRepository.findScheduleByUserId(id);
        return allUserIdSchedule;
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String content, String pwd) {
        if (content == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content are required values.");
        }
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        int updatedRow = scheduleRepository.updateSchedule(schedule.getId(), content, pwd);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
        return new ScheduleResponseDto(schedule);
    }


    @Override
    public void deleteSchedule(Long id, String pwd) {
        int deletedRow = scheduleRepository.deleteSchedule(id, pwd);
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}

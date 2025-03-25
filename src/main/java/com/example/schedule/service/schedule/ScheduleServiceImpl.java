package com.example.schedule.service.schedule;

import com.example.schedule.dto.schedule.ScheduleRequestDto;
import com.example.schedule.dto.schedule.ScheduleResponseDto;
import com.example.schedule.entity.schedule.Schedule;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ExceptionErrorCode;
import com.example.schedule.repository.schedule.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Schedule schedule = getScheduleOrThrow(id);
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
        checkContent(content);
        validatePassword(id, pwd);

        scheduleRepository.updateSchedule(id, content, pwd);
        Schedule schedule = getScheduleOrThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, String pwd) {
        validatePassword(id, pwd);
        scheduleRepository.deleteSchedule(id, pwd);
    }

    
    // 처리용 메소드
    public void checkContent(String content){
        if(content==null){
            throw new CustomException(ExceptionErrorCode.CONTENT_NULL);
        }
    }

    private Schedule getScheduleOrThrow(Long id) {
        return scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.SCHEDULE_NOT_FOUND));
    }

    private void validatePassword(Long id, String pwd) {
        Optional<String> storedPwd = scheduleRepository.findUserPwd(id,pwd);

        if(storedPwd.isEmpty()){
            throw new CustomException(ExceptionErrorCode.SCHEDULE_NOT_FOUND);
        }

        if (!storedPwd.get().equals(pwd)) {
            throw new CustomException(ExceptionErrorCode.PASSWORD_ERROR);
        }


    }


}

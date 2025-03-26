package com.example.schedule.service.schedule;

import com.example.schedule.dto.schedule.ScheduleRequestDto;
import com.example.schedule.dto.schedule.ScheduleResponseDto;
import com.example.schedule.entity.schedule.Schedule;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ExceptionErrorCode;
import com.example.schedule.repository.schedule.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /*******************************
     * requestDto 데이터로 Schedule 저장 - SimpleJDBC 사용
     *******************************/
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        String userName = findUser(requestDto.getUser_id());

        Schedule schedule = new Schedule(
                requestDto.getUser_id(),
                userName,
                requestDto.getContent(),
                requestDto.getPwd()
        );
        return scheduleRepository.saveSchedule(schedule);
    }

    /*******************************
     * 일정 전체 조회
     *******************************/
    @Override
    public List<ScheduleResponseDto> findAllSchedule() {
        List<ScheduleResponseDto> allSchedule = scheduleRepository.findAllSchedules();
        return allSchedule;
    }

    /*******************************
     * 일정id 조건 조회
     *******************************/
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = getScheduleOrThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    /*******************************
     * 일정userId 조건 조회
     *******************************/
    @Override
    public List<ScheduleResponseDto> findScheduleByUserId(Long id) {
        List<ScheduleResponseDto> allUserIdSchedule = scheduleRepository.findScheduleByUserId(id);
        return allUserIdSchedule;
    }

    /*******************************
     * 일정 수정
     *******************************/
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id,ScheduleRequestDto requestDto) {

        //예외처리 영역
        validatePassword(id, requestDto.getPwd()); //id, pwd가 일치하는지 확인

        
        scheduleRepository.updateSchedule(id, requestDto.getContent());
        Schedule schedule = getScheduleOrThrow(id); //변경 후 결과값
        return new ScheduleResponseDto(schedule);
    }

    /*******************************
     * 일정 삭제
     *******************************/
    @Override
    public void deleteSchedule(Long id, String pwd) {
        validatePassword(id, pwd);  //id, pwd 일치 확인
        scheduleRepository.deleteSchedule(id);
    }

    /*******************************
     * 일정 조회 - 페이지네이션
     * pageNum, pageSize 수신 후 Repository 전달
     *******************************/
    @Override
    public List<ScheduleResponseDto> findSchedulesPage(int pageNum, int pageSize) {
        List<ScheduleResponseDto> schedules = scheduleRepository.findSchedulesPage(pageNum, pageSize);

        //쿼리 실행 결과가 비어있는 경우 emptyList 반환
        if (schedules.isEmpty()) {
            return Collections.emptyList();
        }

        return schedules;
    }


    /*******************************
     * 예외처리 위한 메소드 목록
     *******************************/

    /*******************************
     * 찾고자 하는 id의 일정이 존재하지 않는 경우 예외처리
     *******************************/
    private Schedule getScheduleOrThrow(Long id) {
        return scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.SCHEDULE_NOT_FOUND));
    }


    /*******************************
     * 수정/삭제의 id,pwd가 일치하지 않을경우, 해당 id가 존재하지 않을 경우 예외처리
     *******************************/
    private void validatePassword(Long id, String pwd) {
        Optional<String> storedPwd = scheduleRepository.findUserPwd(id);

        if(storedPwd.isEmpty()){
            throw new CustomException(ExceptionErrorCode.SCHEDULE_NOT_FOUND);
        }

        if (!storedPwd.get().equals(pwd)) {
            throw new CustomException(ExceptionErrorCode.PASSWORD_ERROR);
        }
    }

    /*******************************
     * 일정의 userId값으로 조회 시 없는경우 예외처리
     *******************************/
    private String findUser(Long id) {
        Optional<String> userName = scheduleRepository.findUserName(id);

        if(userName.isEmpty()){
            throw new CustomException(ExceptionErrorCode.USER_NOT_FOUND);
        }

        return userName.get();
    }


}

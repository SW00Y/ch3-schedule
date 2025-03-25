package com.example.schedule.controller.schedule;

import com.example.schedule.dto.schedule.ScheduleRequestDto;
import com.example.schedule.dto.schedule.ScheduleResponseDto;
import com.example.schedule.service.schedule.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "일정 관리", description = "일정(Schedule) API")
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 일정 생성 API
     * @param requestDto 일정 생성 요청 데이터
     * @return 생성된 일정의 정보
     */
    @Operation(summary = "일정 생성", description = "새로운 일정 등록")
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(requestDto), HttpStatus.CREATED);
    }

    /**
     * 일정 전체 조회 API
     * @return 전체 일정
     */
    @Operation(summary = "전체 일정 조회", description = "전체 일정 조회")
    @GetMapping
    public List<ScheduleResponseDto> findAllSchedule() {
        return scheduleService.findAllSchedule();
    }

    /**
     * 특정 일정 조회 API
     * @param id 일정id
     * @return 조회된 일정 1건
     */
    @Operation(summary = "특정 일정 조회", description = "schedule Id로 일정 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    /**
     * 특정 유저 작성 일정 조회 API
     * @param id 유저id
     * @return 유저id에 맞는 일정 목록
     */
    @Operation(summary = "일정 생성", description = "새로운 일정 등록")
    @GetMapping("/user/{id}")
    public List<ScheduleResponseDto> findScheduleByUserId(@PathVariable Long id) {
        return scheduleService.findScheduleByUserId(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto
    ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id,requestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        scheduleService.deleteSchedule(id, requestDto.getPwd());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<ScheduleResponseDto> findScheduleList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size)
    {

        return scheduleService.findSchedulesPage(page, size);

    }

}

package com.example.schedule.controller.schedule;

import com.example.schedule.dto.schedule.ScheduleRequestDto;
import com.example.schedule.dto.schedule.ScheduleResponseDto;
import com.example.schedule.service.schedule.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
     * @param requestDto 일정 요청 데이터
     * @return 생성된 일정의 정보
     */
    @Operation(summary = "일정 생성", description = "새로운 일정 등록")
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto requestDto) {
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
    @Parameter(name = "id", description = "조회할 일정의 ID", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    /**
     * 특정 유저 작성 일정 조회 API
     * @param id 유저id
     * @return 유저id에 맞는 일정 목록
     */
    @Operation(summary = "특정 유저 일정 조회", description = "user의 id로 일정 조회")
    @Parameter(name = "id", description = "조회할 user의 id", required = true)
    @GetMapping("/user/{id}")
    public List<ScheduleResponseDto> findScheduleByUserId(@PathVariable Long id) {
        return scheduleService.findScheduleByUserId(id);
    }

    /**
     * 일정 내용 수정 API
     * @param id 일정id
     * @param requestDto 일정 요청 데이터
     * @return 수정된 일정 정보
     */
    @Operation(summary = "일정 수정", description = "schedule의 id로 일정 조회, requestDto로 일정 수정 내용 반영")
    @Parameter(name = "id", description = "수정할 schedule의 id", required = true)
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody @Valid ScheduleRequestDto requestDto
    ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id,requestDto), HttpStatus.OK);
    }

    /**
     * 일정 삭제 API
     * @param id 일정id
     * @param requestDto 일정 요청 데이터(비밀번호 pwd)
     * @return 삭제 성공 시 200 응답
     */
    @Operation(summary = "일정 삭제", description = "schedule의 id로 일정 삭제, requestDto의 pwd로 일정 pwd비교")
    @Parameter(name = "id", description = "수정할 schedule의 id", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleRequestDto requestDto) {
        scheduleService.deleteSchedule(id, requestDto.getPwd());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 일정 페이징조회 API
     * @param page 조회할 페이지번호
     * @param size 페이지당 항목 수
     * @return 요청한 페이지에 맞는 일정 목록
     */
    @Operation(summary = "일정 목록 페이징 조회", description = "페이지 번호, 크기 기준 일정 목록 조회")
    @Parameter(name = "page", description = "조회할 페이지 번호 (기본값: 1)", required = false)
    @Parameter(name = "size", description = "페이지당 항목 수 (기본값: 3)", required = false)
    @GetMapping("/list")
    public List<ScheduleResponseDto> findScheduleList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size)
    {

        return scheduleService.findSchedulesPage(page, size);

    }

}

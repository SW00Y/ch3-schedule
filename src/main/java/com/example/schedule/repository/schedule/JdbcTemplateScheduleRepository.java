package com.example.schedule.repository.schedule;

import com.example.schedule.dto.schedule.ScheduleResponseDto;
import com.example.schedule.entity.schedule.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    /*******************************
     * 일정 저장 SimpleJdbc 사용
     *******************************/
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        //key를 받기위해 설정
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", schedule.getUser_id());
        parameters.put("content", schedule.getContent());
        parameters.put("pwd", schedule.getPwd());
        parameters.put("add_log", schedule.getAdd_log());
        parameters.put("upp_log", schedule.getUpp_log());

        //생성된 key 반환
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        //ResponseDto에 담아서 반환
        return new ScheduleResponseDto(key.longValue(),schedule.getUser_id(),schedule.getName(),schedule.getContent(),schedule.getPwd(),schedule.getAdd_log(),schedule.getUpp_log());
    }

    /*******************************
     * 유저 이름 조회 - 일정 저장 후 결과값에 사용
     *******************************/
    @Override
    public Optional<String> findUserName(Long userId) {
        String sql = "SELECT name FROM user WHERE id = ?";
        List<String> result = jdbcTemplate.queryForList(sql, String.class, userId);
        return result.stream().findFirst();
    }

    /*******************************
     * 일정 전체 조회
     *******************************/
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        String sql = "SELECT a.id, a.user_id, b.name, a.content, a.pwd, a.add_log, a.upp_log " +
                "FROM schedule a JOIN user b ON a.user_id = b.id ORDER BY a.upp_log DESC";
        return jdbcTemplate.query(sql, scheduleRowMapper());
    }

    /*******************************
     * 일정Id로 일정 조회 - 1건
     *******************************/
    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        String sql = "SELECT a.id, a.user_id, b.name, a.content, a.pwd, a.add_log, a.upp_log " +
                "FROM schedule a JOIN user b ON a.user_id = b.id WHERE a.id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapperV2(), id);
        return result.stream().findFirst();
    }

    /*******************************
     * 유저Id로 일정 조회 - n건
     *******************************/
    @Override
    public List<ScheduleResponseDto> findScheduleByUserId(Long id) {
        String sql = "SELECT a.id, a.user_id, b.name, a.content, a.pwd, a.add_log, a.upp_log " +
                "FROM schedule a JOIN user b ON a.user_id = b.id WHERE b.id = ?";

        //결과값을 Mapper이용 Dto로 반환
        return jdbcTemplate.query(sql, scheduleRowMapper(),id);
    }

    /*******************************
     * 유저Id로 유저 pwd 조회 - 1건
     *******************************/
    @Override
    public Optional<String> findUserPwd(Long id) {
        String sql = "SELECT pwd FROM schedule WHERE id = ?";
        List<String> result = jdbcTemplate.queryForList(sql, String.class, id);
        return result.stream().findFirst();
    }

    /*******************************
     * 일정 수정
     *******************************/
    @Override
    public int updateSchedule(Long id, String content) {
        return jdbcTemplate.update("update schedule set content =?, upp_log =? where id=?", content, Timestamp.valueOf(LocalDateTime.now()), id);
    }

    /*******************************
     * 일정 삭제
     *******************************/
    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("delete from schedule where id=?", id);
    }

    /*******************************
     * 페이지네이션
     *******************************/
    @Override
    public List<ScheduleResponseDto> findSchedulesPage(int pageNum, int pageSize) {
        String sql = "SELECT a.id, a.user_id, b.name, a.content, a.pwd, a.add_log, a.upp_log " +
                "FROM schedule a JOIN user b ON a.user_id = b.id ORDER BY a.upp_log DESC LIMIT ? OFFSET ?";
        
        //시작 위치
        int offset = (pageNum - 1) * pageSize;

        return jdbcTemplate.query(sql,scheduleRowMapper(),pageSize,offset);
    }

    //쿼리 실행 결과값을 ScheduleResponseDto로 ㄱ변환
    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("content"),
                        rs.getString("pwd"),
                        rs.getTimestamp("add_log"),
                        rs.getTimestamp("upp_log")
                );
            }
        };
    }

    //결과값을 Schedule로 변환 -> service에서 DTO변환
    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("content"),
                        rs.getString("pwd"),
                        rs.getTimestamp("add_log"),
                        rs.getTimestamp("upp_log")
                );
            }
        };
    }
}

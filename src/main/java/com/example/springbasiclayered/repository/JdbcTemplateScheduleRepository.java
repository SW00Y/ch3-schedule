package com.example.springbasiclayered.repository;

import com.example.springbasiclayered.dto.ScheduleResponseDto;
import com.example.springbasiclayered.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", schedule.getUser_id());
        parameters.put("content", schedule.getContent());
        parameters.put("pwd", schedule.getPwd());
        parameters.put("add_log", schedule.getAdd_log());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(),schedule.getUser_id(),schedule.getName(),schedule.getContent(),schedule.getPwd(),schedule.getAdd_log(),schedule.getUpp_log());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        String sql = "SELECT a.id, a.user_id, b.name, a.content, a.pwd, a.add_log, a.upp_log " +
                "FROM schedule a JOIN user b ON a.user_id = b.id ORDER BY a.upp_log DESC";
        return jdbcTemplate.query(sql, scheduleRowMapper());
    }

    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        String sql = "SELECT a.id, a.user_id, b.name, a.content, a.pwd, a.add_log, a.upp_log " +
                "FROM schedule a JOIN user b ON a.user_id = b.id WHERE a.id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapperV2(), id);
        return result.stream().findAny();
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        String sql = "SELECT a.id, a.user_id, b.name, a.content, a.pwd, a.add_log, a.upp_log " +
                "FROM schedule a JOIN user b ON a.user_id = b.id WHERE a.id = ?";

        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public List<ScheduleResponseDto> findScheduleByUserId(Long id) {
        String sql = "SELECT a.id, a.user_id, b.name, a.content, a.pwd, a.add_log, a.upp_log " +
                "FROM schedule a JOIN user b ON a.user_id = b.id WHERE b.id = ?";

        return jdbcTemplate.query(sql, scheduleRowMapper(),id);
    }

    @Override
    public int updateSchedule(Long id, String content, String pwd) {


        String getPwdSql = "SELECT pwd FROM schedule WHERE id = ?";
        String schedulePwd = jdbcTemplate.queryForObject(getPwdSql, String.class, id);

        if (schedulePwd == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "그런 일정이 없음");
        }

        if (!schedulePwd.equals(pwd)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        return jdbcTemplate.update("update schedule set content =?, upp_log =? where id=?", content, Timestamp.valueOf(LocalDateTime.now()), id);
    }

    @Override
    public int deleteSchedule(Long id, String pwd) {
        return jdbcTemplate.update("delete from schedule where id=?", id);
    }

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

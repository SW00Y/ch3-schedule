package com.example.schedule.repository.user;

import com.example.schedule.dto.user.UserResponseDto;
import com.example.schedule.entity.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*******************************
     * 유저 저장 SimpleJdbc 사용
     *******************************/
    @Override
    public UserResponseDto saveUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        //key를 받기위해 설정
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());
        parameters.put("add_log", user.getAdd_log());

        //생성된 key 반환
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        //ResponseDto에 담아서 반환
        return new UserResponseDto(key.longValue(),user.getName(),user.getEmail(),user.getAdd_log());
    }


    /*******************************
     * 유저 조회
     *******************************/
    @Override
    public Optional<User> findUserById(Long id) {
        String sql = "SELECT a.id, a.name, a.email, a.add_log FROM user a WHERE a.id = ?";
        List<User> result = jdbcTemplate.query(sql, userRowMapper(), id);
        
        //Optional반환으로 null값 예외 방지
        return result.stream().findFirst(); 
    }

    /*******************************
     * 유저 id, email로 조회
     *******************************/
    @Override
    public Optional<User> findUserByIdEmail(Long id, String email) {
        String sql = "SELECT a.id, a.name, a.email, a.add_log FROM user a WHERE a.id = ? and a.email = ?";
        List<User> result = jdbcTemplate.query(sql, userRowMapper(), id, email);
        return result.stream().findFirst();
    }

    /*******************************
     * 유저 수정
     *******************************/
    @Override
    public int updateUser(Long id, String name, String email) {
        return jdbcTemplate.update("update user set name =? where id=? and email=?", name, id, email);
    }

    /*******************************
     * 유저 삭제
     *******************************/
    @Override
    public int deleteUser(Long id, String email) {
        return jdbcTemplate.update("delete from user where id=? and email =?", id, email);
    }

    /*******************************
     *  Query 결과를 User로 변환하기 위한 Mapper
     *******************************/
    private RowMapper<User> userRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("add_log")
                );
            }
        };
    }
}

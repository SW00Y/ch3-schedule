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

    @Override
    public UserResponseDto saveUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());
        parameters.put("add_log", user.getAdd_log());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new UserResponseDto(key.longValue(),user.getName(),user.getEmail(),user.getAdd_log());
    }

    @Override
    public Optional<User> findUserById(Long id) {
        String sql = "SELECT a.id, a.name, a.email, a.add_log FROM user a WHERE a.id = ?";
        List<User> result = jdbcTemplate.query(sql, userRowMapper(), id);
        return result.stream().findFirst();
    }

    @Override
    public Optional<User> findUserByIdEmail(Long id, String email) {
        String sql = "SELECT a.id, a.name, a.email, a.add_log FROM user a WHERE a.id = ? and a.email = ?";
        List<User> result = jdbcTemplate.query(sql, userRowMapper(), id, email);
        return result.stream().findFirst();
    }

    @Override
    public int updateUser(Long id, String name, String email) {
        return jdbcTemplate.update("update user set name =? where id=? and email=?", name, id, email);
    }

    @Override
    public int deleteUser(Long id, String email) {
        return jdbcTemplate.update("delete from user where id=? and email =?", id, email);
    }


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

package com.example.schedule.repository.user;

import com.example.schedule.dto.user.UserResponseDto;
import com.example.schedule.entity.user.User;

import java.util.Optional;

public interface UserRepository {

    UserResponseDto saveUser(User user);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByIdEmail(Long id, String email);

    int updateUser(Long id, String name, String email);

    int deleteUser(Long id, String email);


}

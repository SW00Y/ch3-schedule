package com.example.schedule.service.user;

import com.example.schedule.dto.user.UserRequestDto;
import com.example.schedule.dto.user.UserResponseDto;
import com.example.schedule.entity.user.User;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ExceptionErrorCode;
import com.example.schedule.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto requestDto) {
        User user = new User(
                requestDto.getId(),
                requestDto.getName(),
                requestDto.getEmail()
        );
        checkValidEmail(requestDto.getEmail());

        return userRepository.saveUser(user);
    }

    @Override
    public UserResponseDto findUserById(Long id) {
        User user = getUserOrThrow(id);
        return new UserResponseDto(user);
    }

    @Transactional
    @Override
    public UserResponseDto updateUser(Long id, String name, String email) {

        getUserOrThrowV2(id, email);

        userRepository.updateUser(id, name, email);
        User user = getUserOrThrow(id);
        return new UserResponseDto(user);
    }

    @Override
    public void deleteUser(Long id, String email) {
        getUserOrThrowV2(id, email);
        userRepository.deleteUser(id, email);
    }

    
    // 처리용 메소드
    public void checkValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if(!email.matches(emailRegex)){
            throw new CustomException(ExceptionErrorCode.CHECK_VALUE_MAIL);
        }
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.USER_NOT_FOUND));
    }

    private User getUserOrThrowV2(Long id, String email) {
        return userRepository.findUserByIdEmail(id, email)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.USER_NOT_FOUND));
    }





}

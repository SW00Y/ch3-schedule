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

    /*******************************
     * requestDto 데이터로 user 저장 - SimpleJDBC 사용
     *******************************/
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

    /*******************************
     * userId로 user 조회
     *******************************/
    @Override
    public UserResponseDto findUserById(Long id) {
        User user = getUserOrThrow(id);
        return new UserResponseDto(user);
    }

    /*******************************
     * 유저 수정
     *******************************/
    @Transactional
    @Override
    public UserResponseDto updateUser(Long id, String name, String email) {

        getUserOrThrowV2(id, email); //id, email로 인증

        userRepository.updateUser(id, name, email);
        User user = getUserOrThrow(id);
        return new UserResponseDto(user);
    }

    /*******************************
     * 유저 삭제
     *******************************/
    @Override
    public void deleteUser(Long id, String email) {
        getUserOrThrowV2(id, email); // id, email로 인증
        userRepository.deleteUser(id, email);
    }


    /*******************************
     * 예외처리 메소드 목록
     *******************************/


    /*******************************
     * 정규표현식을 이용한 email 확인
     *******************************/
    public void checkValidEmail(String email){
        // (대소문자,알파벳,숫자, . 가능) + @ + (알파벳,숫자,하이픈) + . + (2~7자 알파벳)
        String emailRegex = "^[a-zA-Z0-9_+&*-] + (?:\\.[a-zA-Z0-9_+&*-]+) * @ (?:[a-zA-Z0-9-]+\\.) + [a-zA-Z]{2,7}$";
        
        //예외처리
        if(!email.matches(emailRegex)){
            throw new CustomException(ExceptionErrorCode.CHECK_VALUE_MAIL);
        }
    }

    /*******************************
     * 유저 조회 시 없는경우
     *******************************/
    private User getUserOrThrow(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.USER_NOT_FOUND));
    }

    /*******************************
     * id, email 이 일치하지 않는 경우 - 조회와 삭제에 사용
     *******************************/
    private User getUserOrThrowV2(Long id, String email) {
        return userRepository.findUserByIdEmail(id, email)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.USER_NOT_FOUND));
    }





}

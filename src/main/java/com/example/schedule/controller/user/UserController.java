package com.example.schedule.controller.user;

import com.example.schedule.dto.user.UserRequestDto;
import com.example.schedule.dto.user.UserResponseDto;
import com.example.schedule.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 정보", description = "유저(User) API")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * 유저 생성 API
     * @param requestDto 유저 생성 정보
     * @return 저장된 유저 정보, 성공시 200
     */
    @Operation(summary = "유저 생성", description = "새로운 유저 생성")
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        return new ResponseEntity<>(userService.saveUser(requestDto), HttpStatus.CREATED);
    }

    /**
     * 특정 유저 조회 API
     * @param id 유저id
     * @return 유저id로 찾은 유저 정보 1건
     */
    @Operation(summary = "특정 유저 조회", description = "user id로 유저 조회")
    @Parameter(name = "id", description = "조회할 유저의 ID", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    /**
     * 유저 정보 수정 API
     * @param id 유저id
     * @param requestDto 유저 정보
     * @return 수정된 유저 정보, 성공시 200
     */
    @Operation(summary = "유저 정보 수정", description = "user Id, email 인증으로 유저 name 수정")
    @Parameter(name = "id", description = "수정할 유저의 ID", required = true)
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto requestDto
    ) {
        return new ResponseEntity<>(userService.updateUser(id,requestDto.getName(),requestDto.getEmail()), HttpStatus.OK);
    }

    /**
     * 유저 삭제 API
     * @param id 유저id
     * @return 성공시 200
     */
    @Operation(summary = "유저 삭제", description = "user Id, email 인증으로 유저 삭제")
    @Parameter(name = "id", description = "삭제할 유저의 ID", required = true)
    @Parameter(name = "email", description = "삭제할 유저의 email", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody UserRequestDto requestDto) {
        userService.deleteUser(id, requestDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

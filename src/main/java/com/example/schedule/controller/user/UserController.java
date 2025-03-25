package com.example.schedule.controller.user;

import com.example.schedule.dto.user.UserRequestDto;
import com.example.schedule.dto.user.UserResponseDto;
import com.example.schedule.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        return new ResponseEntity<>(userService.saveUser(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto requestDto
    ) {
        return new ResponseEntity<>(userService.updateUser(id,requestDto.getName(),requestDto.getEmail()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestParam String email) {
        userService.deleteUser(id, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

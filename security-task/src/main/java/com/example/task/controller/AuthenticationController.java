package com.example.task.controller;

import com.example.task.dto.UserRequestDto;
import com.example.task.dto.UserResponseDto;
import com.example.task.entity.User;
import com.example.task.entity.Role;
import com.example.task.mapper.UserMapper;
import com.example.task.service.AuthenticationService;
import com.example.task.service.JwtService;
import com.example.task.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(
            @RequestBody UserRequestDto userRequestDto) {
        String hashPwd = passwordEncoder.encode(userRequestDto.getPassword());

        userRequestDto.setPassword(hashPwd);

        User user = userMapper.userRequestDtoToUser(userRequestDto);
        user.setRole(Role.USER);
        user = userService.addUser(user);

        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @RequestMapping("/login")
    public ResponseEntity<UserResponseDto> getUserDetailsAfterLogin(
            @RequestBody UserRequestDto userRequestDto
            , HttpServletRequest request
    ) {
        User user = userService.getUserByEmail(userRequestDto.getEmail());

        authenticationService.authenticate(user, request);

        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtService.generateToken(user)
                )
                .body(userResponseDto);
    }
}
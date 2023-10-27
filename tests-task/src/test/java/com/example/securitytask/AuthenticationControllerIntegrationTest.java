package com.example.securitytask;

import com.example.task.TestsTaskApplication;
import com.example.task.dto.UserRequestDto;
import com.example.task.dto.UserResponseDto;
import com.example.task.entity.Role;
import com.example.task.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest(classes = TestsTaskApplication.class)
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private JwtService jwtService;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void testRegisterUser() throws Exception {

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("register@example.com");
        userRequestDto.setPassword("registerPassword");

        String url = "http://localhost:8080/auth/register";

        ResponseEntity<UserResponseDto> responseEntity = restTemplate.postForEntity(url, userRequestDto, UserResponseDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getEmail()).isEqualTo("register@example.com");
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getRole()).isEqualTo("USER");
    }

    @Test
    void testLoginUser() throws Exception {

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("user@gmail.com");
        userRequestDto.setPassword("user");

        String url = "http://localhost:8080/auth/login";

        ResponseEntity<UserResponseDto> responseEntity = restTemplate.postForEntity(url, userRequestDto, UserResponseDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getEmail()).isEqualTo("user@gmail.com");
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getRole()).isEqualTo("USER");

        assertThatNoException().isThrownBy(
                ()->jwtService.extractEmail(
                        String.valueOf(Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0))
                )
        );

        assertThat(jwtService.extractEmail(
                String.valueOf(Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0)))
        ).isEqualTo("user@gmail.com");

        assertThatNoException().isThrownBy(
                ()->jwtService.extractRole(
                        String.valueOf(Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0))
                )
        );

        assertThat(jwtService.extractRole(
                String.valueOf(Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0)))
        ).isEqualTo(Role.USER);
    }
}
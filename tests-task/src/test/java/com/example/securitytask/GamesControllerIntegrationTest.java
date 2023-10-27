package com.example.securitytask;

import com.example.task.TestsTaskApplication;
import com.example.task.dto.GameRequestDto;
import com.example.task.dto.GameResponseDto;
import com.example.task.entity.User;
import com.example.task.service.JwtService;
import com.example.task.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.util.Collections;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestsTaskApplication.class)
public class GamesControllerIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void testAddGameForAdmin() throws Exception {

        User user = userService.getUserByEmail("admin@gmail.com");

        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + jwtService.generateToken(user));
            return execution.execute(request, body);
        };

        restTemplate.getRestTemplate().setInterceptors(Collections.singletonList(interceptor));

        GameRequestDto gameRequestDto = new GameRequestDto();
        gameRequestDto.setName("CS2");
        gameRequestDto.setGenre("Shooter");

        String url = "http://localhost:8080/games/add";

        ResponseEntity<GameResponseDto> responseEntity = restTemplate.postForEntity(url, gameRequestDto, GameResponseDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getName()).isEqualTo("CS2");
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getGenre()).isEqualTo("Shooter");
    }

    @Test
    void testAddGameForUser() throws Exception {

        User user = userService.getUserByEmail("user@gmail.com");

        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + jwtService.generateToken(user));
            return execution.execute(request, body);
        };

        restTemplate.getRestTemplate().setInterceptors(Collections.singletonList(interceptor));

        GameRequestDto gameRequestDto = new GameRequestDto();
        gameRequestDto.setName("LOL");
        gameRequestDto.setGenre("ForKids");

        String url = "http://localhost:8080/games/add";

        ResponseEntity<GameResponseDto> responseEntity = restTemplate.postForEntity(url, gameRequestDto, GameResponseDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }


}
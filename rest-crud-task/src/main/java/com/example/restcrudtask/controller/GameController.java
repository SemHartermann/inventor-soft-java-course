package com.example.restcrudtask.controller;

import com.example.restcrudtask.dto.GameRequestDto;
import com.example.restcrudtask.dto.GameResponseDto;
import com.example.restcrudtask.entity.Game;
import com.example.restcrudtask.mapper.GameMapper;
import com.example.restcrudtask.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
public class GameController {
    private GameService gameService;
    private GameMapper gameMapper;
    @PostMapping
    public ResponseEntity<GameResponseDto> addGame(@RequestBody GameRequestDto gameRequestDto) {
        Game game = gameService.addGame(gameMapper.gameRequestDtoToGame(gameRequestDto));
        GameResponseDto gameResponseDto = gameMapper.gameToGameResponseDto(game);
        return new ResponseEntity<>(gameResponseDto, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GameResponseDto> getGameById(@PathVariable Long id) {
        Game game = gameService.getById(id);
        GameResponseDto gameResponseDto = gameMapper.gameToGameResponseDto(game);
        return new ResponseEntity<>(gameResponseDto, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<GameResponseDto>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        List<GameResponseDto> gameResponseDtos = gameMapper.gamesToGameResponseDtos(games);
        return new ResponseEntity<>(gameResponseDtos, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GameResponseDto> editGameById(@PathVariable Long id,
                                                        @RequestBody GameRequestDto gameRequestDto) {
        Game game = gameService.editById(id, gameRequestDto);
        GameResponseDto gameResponseDto = gameMapper.gameToGameResponseDto(game);
        return new ResponseEntity<>(gameResponseDto, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public HttpStatus deleteGameById(@PathVariable Long id) {
        gameService.getById(id);
        gameService.deleteGameById(id);
        return HttpStatus.NO_CONTENT;
    }
}

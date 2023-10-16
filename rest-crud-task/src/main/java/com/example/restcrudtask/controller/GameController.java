package com.example.restcrudtask.controller;

import com.example.restcrudtask.dto.RequestGameDto;
import com.example.restcrudtask.dto.RespondGameDto;
import com.example.restcrudtask.entity.Game;
import com.example.restcrudtask.exception.GameNotFoundException;
import com.example.restcrudtask.exception.NameConflictException;
import com.example.restcrudtask.mapper.GameMapper;
import com.example.restcrudtask.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<RespondGameDto> addGame(@RequestBody RequestGameDto requestGameDto) throws NameConflictException {

        Game game = gameService.addGame(gameMapper.requestGameDtoToGame(requestGameDto));

        RespondGameDto respondGameDto = gameMapper.gameToRespondGameDto(game);

        return new ResponseEntity<>(respondGameDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespondGameDto> getGameById(@PathVariable Long id) throws GameNotFoundException {

        Game game = gameService.getById(id);

        RespondGameDto respondGameDto = gameMapper.gameToRespondGameDto(game);

        return new ResponseEntity<>(respondGameDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RespondGameDto>> getAllGames(){

        List<Game> games = gameService.getAllGames();

        List<RespondGameDto> respondGameDtos = gameMapper.gamesToRespondGameDtos(games);

        return new ResponseEntity<>(respondGameDtos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespondGameDto> editGameById(@PathVariable Long id,
                                                       @RequestBody RequestGameDto requestGameDto)
            throws GameNotFoundException {

        Game game = gameService.editById(id, requestGameDto);

        RespondGameDto respondGameDto = gameMapper.gameToRespondGameDto(game);

        return new ResponseEntity<>(respondGameDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespondGameDto> deleteGameById(@PathVariable Long id) throws GameNotFoundException {

        Game game = gameService.getById(id);

        gameService.deleteGameById(id);

        RespondGameDto respondGameDto = gameMapper.gameToRespondGameDto(game);

        return new ResponseEntity<>(respondGameDto, HttpStatus.NO_CONTENT);
    }
}

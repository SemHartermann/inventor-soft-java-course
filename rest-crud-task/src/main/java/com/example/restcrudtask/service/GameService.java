package com.example.restcrudtask.service;

import com.example.restcrudtask.dto.RequestGameDto;
import com.example.restcrudtask.entity.Game;
import com.example.restcrudtask.exception.GameNotFoundException;
import com.example.restcrudtask.exception.NameConflictException;
import com.example.restcrudtask.repository.GameRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Setter
public class GameService {

    private GameRepository gameRepository;
    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    public Game getById(Long id) throws GameNotFoundException {
        return gameRepository.findById(id).orElseThrow(
                () -> new GameNotFoundException("Game not found with id: " + id));
    }

    public Game editById(Long id, RequestGameDto gameDto) throws GameNotFoundException {
        Game game = gameRepository.findById(id).orElseThrow(
                () -> new GameNotFoundException("Game not found with id: " + id));
        game = Game.builder()
                .id(game.getId())
                .name(gameDto.getName())
                .genre(gameDto.getGenre())
                .build();

        return gameRepository.save(game);
    }

    public Game addGame(Game game)throws NameConflictException {
        return gameRepository.save(game);
    }

    public void deleteGameById(Long id){
        gameRepository.deleteById(id);
    }
}

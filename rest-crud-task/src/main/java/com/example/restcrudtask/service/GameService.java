package com.example.restcrudtask.service;

import com.example.restcrudtask.dto.GameRequestDto;
import com.example.restcrudtask.entity.Game;
import com.example.restcrudtask.repository.GameRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Setter
public class GameService {

    private GameRepository gameRepository;
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }
    public Game getById(Long id) {
        return gameRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Game not found with id: " + id));
    }
    public Game editById(Long id, GameRequestDto gameDto) {
        Game game = gameRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Game not found with id: " + id));
        game = Game.builder()
                .id(game.getId())
                .name(gameDto.getName())
                .genre(gameDto.getGenre())
                .build();

        return gameRepository.save(game);
    }
    public Game addGame(Game game) {
        return gameRepository.save(game);
    }
    public void deleteGameById(Long id) {
        gameRepository.deleteById(id);
    }
}

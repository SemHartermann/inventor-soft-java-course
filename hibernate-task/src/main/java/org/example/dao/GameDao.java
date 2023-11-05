package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.entity.Game;

import java.util.List;

public interface GameDao {
    Game addGame(Game game);

    List<Game> findGames();

    Game findGameById(Long id);

    void updateGame(Game game);

    void deleteGame(Game game);

    void injectEntityManager(EntityManager entityentityManager);
}
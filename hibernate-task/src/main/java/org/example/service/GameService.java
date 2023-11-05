package org.example.service;

import org.example.entity.Game;

import java.sql.SQLException;
import java.util.List;

public interface GameService {
    Game addGame(Game game) throws SQLException;

    List<Game> findGames() throws SQLException;

    Game findGameById(Long id) throws SQLException;

    void deleteGameById(Long id) throws SQLException;

    Game updateGame(Game game) throws SQLException;

}

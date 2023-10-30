package org.example.service;

import org.example.dao.GameDao;
import org.example.dao.h2impl.GameDaoHTwoImpl;
import org.example.entity.Game;

import java.sql.SQLException;
import java.util.List;

public interface GameService {
    GameDao gameDao = new GameDaoHTwoImpl();

    Game addGame(Game game) throws SQLException;

    List<Game> findGames() throws SQLException;

    Game findGameById(Long id) throws SQLException;

    boolean deleteGameById(Long id) throws SQLException;

    Game updateGame(Game game) throws SQLException;

}

package org.example.service;

import org.example.entity.Game;

import java.sql.SQLException;
import java.util.List;

public class GameServiceImpl implements GameService {
    @Override
    public Game addGame(Game game) throws SQLException {
        return gameDao.addGame(game);
    }

    @Override
    public List<Game> findGames() throws SQLException {
        return gameDao.findGames();
    }

    @Override
    public Game findGameById(Long id) throws SQLException {
        return gameDao.findGameById(id);
    }

    @Override
    public boolean deleteGameById(Long id) throws SQLException {
        return gameDao.deleteGameById(id);
    }

    @Override
    public Game updateGame(Game game) throws SQLException {
        gameDao.updateGame(game);

        return game;
    }
}

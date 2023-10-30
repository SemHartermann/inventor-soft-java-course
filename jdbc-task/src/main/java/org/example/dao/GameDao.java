package org.example.dao;

import org.example.entity.Game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface GameDao {
    Game addGame(Game game) throws SQLException;

    Game extractGame(ResultSet rs) throws SQLException;

    List<Game> findGames() throws SQLException;

    Game findGameById(Long id) throws SQLException;

    void updateGame(Game game) throws SQLException;

    boolean deleteGameById(Long id) throws SQLException;
}
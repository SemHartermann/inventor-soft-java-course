package org.example.dao.h2impl;

import org.apache.log4j.Logger;
import org.example.DBManager;
import org.example.dao.GameDao;
import org.example.entity.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GameDaoHTwoImpl implements GameDao {

    private static final Logger LOG = Logger.getLogger(GameDaoHTwoImpl.class);

    private static final String SQL_ADD_NEW_GAME = "INSERT INTO game (name, genre, id) VALUES (?, ?, default)";
    private static final String SQL_FIND_ALL_GAMES = "SELECT * FROM game";
    private static final String SQL_FIND_GAME_BY_ID = "SELECT * FROM game WHERE id=?";
    private static final String SQL_UPDATE_GAME = "UPDATE game SET (name, genre) = (?, ?) WHERE id=?";
    private static final String SQL_DELETE_GAME_BY_ID = "DELETE FROM game WHERE id=?";


    public boolean deleteGameById(Long id) throws SQLException {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_DELETE_GAME_BY_ID))
        {
            preparedStatement.setLong(1, id);
            int count = preparedStatement.executeUpdate();
            if (count < 1) {
                return false;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SQLException(ex);
        }
        return true;
    }

    public Game addGame(Game game) throws SQLException {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_ADD_NEW_GAME)) {
            preparedStatement.setString(1, game.getName());
            preparedStatement.setString(2, game.getGenre());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SQLException(ex);
        }
        return game;
    }

    public Game extractGame(ResultSet rs) throws SQLException {
        Game game;

        try {
            game = Game
                    .builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .genre(rs.getString("genre"))
                    .build();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SQLException(ex);
        }
        return game;
    }

    public List<Game> findGames() throws SQLException {
        List<Game> GameList = new ArrayList<>();

        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_FIND_ALL_GAMES)) {
            while (rs.next()) {
                GameList.add(extractGame(rs));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SQLException(ex);
        }
        return GameList;
    }

    @Override
    public Game findGameById(Long id) throws SQLException {
        Game Game = null;

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_FIND_GAME_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Game = extractGame(rs);
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SQLException(ex);
        }

        return Game;
    }

    public void updateGame(Game game) throws SQLException {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_UPDATE_GAME)) {
            preparedStatement.setString(1, game.getName());
            preparedStatement.setString(2, game.getGenre());
            preparedStatement.setLong(3, game.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SQLException(ex);
        }
    }
}
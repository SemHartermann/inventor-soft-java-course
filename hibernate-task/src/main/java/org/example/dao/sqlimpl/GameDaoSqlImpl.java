package org.example.dao.sqlimpl;

import jakarta.persistence.EntityManager;
import org.example.dao.GameDao;
import org.example.entity.Game;

import java.util.List;

public class GameDaoSqlImpl implements GameDao {
    private EntityManager entityManager;

    public void injectEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Game addGame(Game game) {
        entityManager.persist(game);

        return game;
    }

    @Override
    public List<Game> findGames() {
        return entityManager
                .createQuery("select g from Game g", Game.class)
                .getResultList();
    }

    @Override
    public Game findGameById(Long id) {
        return entityManager.find(Game.class, id);
    }

    @Override
    public void updateGame(Game game) {
        entityManager.merge(game);
    }

    @Override
    public void deleteGame(Game game) {
        Game mergedGame = entityManager.merge(game);
        entityManager.remove(mergedGame);
    }
}
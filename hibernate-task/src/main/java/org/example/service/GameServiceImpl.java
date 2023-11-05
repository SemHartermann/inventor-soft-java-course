package org.example.service;

import org.example.dao.sqlimpl.unitofwork.UnitOfWork;
import org.example.entity.Game;

import java.util.List;

public class GameServiceImpl implements GameService {
    @Override
    public Game addGame(Game game){
        game.setId(null);

        return UnitOfWork.performWithReturning(
                () -> UnitOfWork
                        .getGameDao()
                        .addGame(game)
        );
    }

    @Override
    public List<Game> findGames(){
        return UnitOfWork.performForOnlyGetWithReturning(
                () -> UnitOfWork
                        .getGameDao()
                        .findGames()
        );
    }

    @Override
    public Game findGameById(Long id){
        return UnitOfWork.performForOnlyGetWithReturning(
                () -> UnitOfWork
                        .getGameDao()
                        .findGameById(id)
        );
    }

    @Override
    public void deleteGameById(Long id){

        Game game = UnitOfWork.performWithReturning(
                () -> UnitOfWork
                        .getGameDao()
                        .findGameById(id)
        );

        UnitOfWork.perform(
                () -> UnitOfWork
                        .getGameDao()
                        .deleteGame(game)
                );
    }

    @Override
    public Game updateGame(Game game){
        UnitOfWork.perform(
                () -> UnitOfWork
                        .getGameDao()
                        .updateGame(game)
        );

        return game;
    }
}

package org.example;

import org.example.dao.sqlimpl.unitofwork.UnitOfWork;
import org.example.entity.Game;
import org.example.entity.User;
import org.example.service.GameService;
import org.example.service.GameServiceImpl;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;
import org.example.util.DataLoader;
import org.example.util.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

public class MainHTwo {
    public static void main(String[] args) throws SQLException, GeneralSecurityException, UnsupportedEncodingException {
        /**Create H2 Persistence Unit*/
        UnitOfWork.init("BasicEntitiesH2");

        /**Seed data*/
        DataLoader.seedData();

        /**Testing Game (Data Access and Service)*/
        GameService gameService = new GameServiceImpl();

        System.out.println(gameService.findGames() + "\n");

        System.out.println(gameService.addGame(Game
                                                   .builder()
                                                   .name("WoW")
                                                   .genre("MMO RPG")
                                                   .build()) + "\n");

        System.out.println(gameService.findGames() + "\n");

        System.out.println(gameService.updateGame(Game
                                                      .builder()
                                                      .id(3L)
                                                      .name("LoL")
                                                      .genre("moba")
                                                      .build()) + "\n");

        System.out.println(gameService.findGames() + "\n");

        System.out.println(gameService.findGameById(3L)+"\n");

        System.out.println(gameService.findGames() + "\n");

        gameService.deleteGameById(3L);

        System.out.println(gameService.findGames() + "\n");

        /**Testing User (Data Access and Service)*/
        UserService userService = new UserServiceImpl();

        System.out.println(userService.findUsers() + "\n");

        System.out.println(userService.addUser(User
                                                   .builder()
                                                   .email("yatorogod@gmail.com")
                                                   .password(PasswordEncoder.encode("yatorogod"))
                                                   .build()) + "\n");

        System.out.println(userService.findUsers() + "\n");

        System.out.println(userService.updateUser(User
                                                      .builder()
                                                      .id(3L)
                                                      .email("larl@gmail.com")
                                                      .password(PasswordEncoder.encode("larl"))
                                                      .build()) + "\n");

        System.out.println(userService.findUsers() + "\n");

        System.out.println(userService.findUserById(3L) + "\n");

        userService.deleteUserById(3L);

        System.out.println(userService.findUsers() + "\n");

        /**Close H2 Persistence Unit*/
        UnitOfWork.close();
    }
}
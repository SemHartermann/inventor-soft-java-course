package org.example.util;

import org.example.dao.sqlimpl.unitofwork.UnitOfWork;
import org.example.entity.Game;
import org.example.entity.Role;
import org.example.entity.User;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public class DataLoader {
    static public void seedData() throws GeneralSecurityException, UnsupportedEncodingException {

        User admin = User.builder()
                .email("admin@gmail.com")
                .password(PasswordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .build();

        User user = User.builder()
                .email("user@gmail.com")
                .password(PasswordEncoder.encode("user"))
                .role(Role.USER)
                .build();

        Game game1 = Game.builder()
                .name("Dota2")
                .genre("moba")
                .build();

        Game game2 = Game.builder()
                .name("Hearthstone")
                .genre("carto4ki")
                .build();

        UnitOfWork.perform(() -> {
            UnitOfWork.getGameDao().addGame(game1);
            UnitOfWork.getGameDao().addGame(game2);
            UnitOfWork.getUserDao().addUser(admin);
            UnitOfWork.getUserDao().addUser(user);
        });
    }
}

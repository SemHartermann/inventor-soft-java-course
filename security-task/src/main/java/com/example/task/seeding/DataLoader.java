package com.example.task.seeding;

import com.example.task.entity.Game;
import com.example.task.entity.Role;
import com.example.task.entity.User;
import com.example.task.repository.GameRepository;
import com.example.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
        loadGameData();
    }
    private void loadUserData() {
        if (userRepository.count() == 0) {
            User user1 = new User(1L
                    ,"andreydamluley@gmail.com"
                    , passwordEncoder.encode("kiev123")
                    , Role.ADMIN);
            User user2 = new User(2L
                    ,"bebraspirit@gmail.com"
                    , passwordEncoder.encode("kharkiv456")
                    , Role.USER);
            userRepository.save(user1);
            userRepository.save(user2);
        }
    }

    private void loadGameData() {
        if (gameRepository.count() == 0) {
            Game user1 = Game.builder()
                    .name("Dota2")
                    .genre("moba")
                    .build();
            Game user2 = Game.builder()
                    .name("Hearthstone")
                    .genre("carto4ki")
                    .build();
            gameRepository.save(user1);
            gameRepository.save(user2);
        }
    }
}
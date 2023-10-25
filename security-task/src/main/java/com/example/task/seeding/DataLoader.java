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
            User admin = new User(1L
                    ,"admin@gmail.com"
                    , passwordEncoder.encode("admin")
                    , Role.ADMIN);

            User user = new User(2L
                    ,"user@gmail.com"
                    , passwordEncoder.encode("user")
                    , Role.USER);

            userRepository.save(admin);
            userRepository.save(user);
        }
    }

    private void loadGameData() {
        if (gameRepository.count() == 0) {
            Game game1 = Game.builder()
                    .name("Dota2")
                    .genre("moba")
                    .build();

            Game game2 = Game.builder()
                    .name("Hearthstone")
                    .genre("carto4ki")
                    .build();

            gameRepository.save(game1);
            gameRepository.save(game2);
        }
    }
}
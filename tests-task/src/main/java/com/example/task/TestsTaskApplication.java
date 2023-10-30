package com.example.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TestsTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestsTaskApplication.class, args);
    }
}

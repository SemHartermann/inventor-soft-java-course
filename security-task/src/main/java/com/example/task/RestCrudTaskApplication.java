package com.example.task;

import com.example.task.entity.Role;
import com.example.task.entity.User;
import com.example.task.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@SpringBootApplication
public class RestCrudTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestCrudTaskApplication.class, args);
    }

}

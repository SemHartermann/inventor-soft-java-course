package com.example.task.service;

import com.example.task.entity.Role;
import com.example.task.entity.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.lang.Maps;
import jakarta.xml.bind.DatatypeConverter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@NoArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    public Role extractRole(String token) throws JwtException {
        return Jwts.parser()
                .json(new JacksonDeserializer(Maps.of("role", Role.class).build()))
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", Role.class);
    }

    public String extractEmail(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String generateToken(User user){
        Map<String, Role> claims = new HashMap<>();
        claims.put("role", user.getRole());

        return Jwts
                .builder()
                .subject(user.getUsername())
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 1000 *60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private SecretKey getSignInKey(){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());
    }
}

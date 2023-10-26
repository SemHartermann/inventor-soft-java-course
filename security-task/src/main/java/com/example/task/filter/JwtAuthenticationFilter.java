package com.example.task.filter;

import com.example.task.entity.User;
import com.example.task.service.AuthenticationService;
import com.example.task.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            notValidTokenResponse(response);
            return;
        }

        String jwt = authHeader.split(" ")[1].trim();

        try{
            UserDetails userDetails = User
                                        .builder()
                                        .email(jwtService.extractEmail(jwt))
                                        .role(jwtService.extractRole(jwt))
                                        .build();

            authenticationService.authenticate(userDetails, request);

        } catch (JwtException ex){
            notValidTokenResponse(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void notValidTokenResponse(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/auth/login")
                || request.getServletPath().equals("/auth/register")
                || request.getServletPath().equals("/form")
                || request.getServletPath().equals("/csrf")
                || request.getServletPath().equals("/h2");
    }

}

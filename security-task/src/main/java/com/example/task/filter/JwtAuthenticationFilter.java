package com.example.task.filter;

import com.example.task.entity.User;
import com.example.task.service.AuthenticationService;
import com.example.task.service.JwtService;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
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


        if (!jwtService.isTokenValid(jwt)) {
            notValidTokenResponse(response);
            return;
        }

        UserDetails userDetails = jwtService.extractUser(jwt);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            authenticationService.authenticate(userDetails, request);
        }

        filterChain.doFilter(request, response);
    }

    private void notValidTokenResponse(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/login")
                || request.getServletPath().equals("/register")
                || request.getServletPath().equals("/form")
                || request.getServletPath().equals("/csrf")
                || request.getServletPath().equals("/h2");
    }

}

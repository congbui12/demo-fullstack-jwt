package com.example.jwt_v1.service;

import com.example.jwt_v1.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;

        if(header == null || !header.startsWith("Bearer ")) {
            return;
        }

        jwt = header.substring(7);
        var storeToken = tokenRepository.findByToken(jwt).orElse(null);
        storeToken.setExpired(true);
        storeToken.setRevoked(true);
        tokenRepository.save(storeToken);
    }
}

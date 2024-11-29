package com.example.jwt_v1.service;

import com.example.jwt_v1.dto.JwtAuthResponse;
import com.example.jwt_v1.dto.LoginRequest;
import com.example.jwt_v1.dto.SignupRequest;
import com.example.jwt_v1.entity.Token;
import com.example.jwt_v1.entity.TokenType;
import com.example.jwt_v1.entity.User;
import com.example.jwt_v1.exception.AppException;
import com.example.jwt_v1.repository.TokenRepository;
import com.example.jwt_v1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public User signup(SignupRequest signupRequest) {
        Optional<User> existUser = userRepository.findByUsername(signupRequest.getUsername());
        if (existUser.isPresent()) {
            throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(signupRequest.getRole())
                .build();

        return userRepository.save(user);
    }

    public JwtAuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new AppException("Bad credentials", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtService.generateToken(user);
        revokeAllTokensByUser(user);

        Token jwtToken = Token.builder()
                .token(token)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();
        tokenRepository.save(jwtToken);

        return JwtAuthResponse.builder().token(token).build();
    }

    public void revokeAllTokensByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllValidTokensByUser(user.getId());

        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });

        tokenRepository.saveAll(validTokens);
    }
}

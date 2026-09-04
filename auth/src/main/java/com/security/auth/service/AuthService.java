package com.security.auth.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.security.auth.dto.AuthResponse;
import com.security.auth.dto.LoginRequest;
import com.security.auth.dto.RegisterRequest;
import com.security.auth.entity.Role;
import com.security.auth.entity.User;
import com.security.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;




    public AuthResponse register(RegisterRequest request){
    	
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException(
                    "Username already exists"
            );
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException(
                    "Email already exists"
            );
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(Role.ROLE_USER)
                .enabled(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .build();
        userRepository.save(user);
        String token =
                jwtService.generateToken(user);
        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(86400000L)
                .build();
    }
    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user =
                userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(
                        () ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        String token =
                jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(86400000L)
                .build();
    }
}
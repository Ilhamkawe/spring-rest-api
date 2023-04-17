package com.kawe.belajarrest.services.Authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kawe.belajarrest.dao.AuthRepository;
import com.kawe.belajarrest.dto.request.AuthenticationRequestDTO;
import com.kawe.belajarrest.dto.request.RegisterRequestDTO;
import com.kawe.belajarrest.dto.response.AuthenticationResponse;
import com.kawe.belajarrest.entity.Role;
import com.kawe.belajarrest.entity.Auth;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final AuthRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequestDTO request){
        var user = Auth.builder()
        .username(request.username)
        .password(passwordEncoder.encode(request.password))
        .no_telp(request.noTelp)
        .role(Role.ADMIN)
        .domisili(request.domisili)
        .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequestDTO request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword())
        );

        var user = repository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user); 
        
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}

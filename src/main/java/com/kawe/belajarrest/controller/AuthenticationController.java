package com.kawe.belajarrest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kawe.belajarrest.dto.request.AuthenticationRequestDTO;
import com.kawe.belajarrest.dto.request.RegisterRequestDTO;
import com.kawe.belajarrest.dto.response.AuthenticationResponse;
import com.kawe.belajarrest.services.Authentication.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

private final AuthenticationService service;
    
@PostMapping("/register")
public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequestDTO request){
    return ResponseEntity.ok(service.register(request));
}

@PostMapping("/authenticate")
public ResponseEntity<AuthenticationResponse> Authenticate(@RequestBody AuthenticationRequestDTO request){
    return ResponseEntity.ok(service.authenticate(request));
}

}

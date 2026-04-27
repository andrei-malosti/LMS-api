package com.lms.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.api.dto.LoginRequestDTO;
import com.lms.api.dto.LoginResponseDTO;
import com.lms.api.dto.RegisterRequestDTO;
import com.lms.api.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<?> create(@RequestBody @Valid RegisterRequestDTO registerRequest){
		authService.register(registerRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest){
		return ResponseEntity.ok(authService.login(loginRequest));
	}
	
}

package com.microservice.api_auth_service.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.api_auth_service.dto.AuthRequestDto;
import com.microservice.api_auth_service.util.JwtUtil;

@RestController
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto) {
		// Avoid DB user fetching and hard corded for take-home assessment
		if ("admin".equals(authRequestDto.getUsername()) && "admin".equals(authRequestDto.getPassword())) {
			String token = jwtUtil.generateToken(authRequestDto.getUsername());
			return ResponseEntity.ok(new HashMap<String, String>() {
				{
					put("token", token);
				}
			});
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	}

	@PostMapping("/validate")
	public ResponseEntity<?> validate(@RequestHeader("Authorization") String token) {
		try {
			jwtUtil.validateToken(token.replace("Bearer ", ""));
			return ResponseEntity.ok("Valid Token");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
		}
	}

	@GetMapping("/validate")
	public ResponseEntity<?> validateget(@RequestHeader("Authorization") String token) {
		try {
			jwtUtil.validateToken(token.replace("Bearer ", ""));
			return ResponseEntity.ok("Valid Token");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
		}
	}

}

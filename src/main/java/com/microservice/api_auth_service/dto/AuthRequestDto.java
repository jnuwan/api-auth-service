package com.microservice.api_auth_service.dto;

import lombok.Data;

@Data
public class AuthRequestDto {

	private String username;
	private String password;
	
}

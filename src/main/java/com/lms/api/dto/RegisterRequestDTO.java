package com.lms.api.dto;

import com.lms.api.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterRequestDTO {
	
	@NotBlank(message = "Nome é obrigatorio")
	private String name;
	
	@NotBlank(message = "Email é obrigatorio")
	@Email(message = "Email invalido ou inexistente")
	private String email;
	
	@NotBlank(message = "A senha é obrigatoria")
	private String password;
	
	@NotNull(message = "é necessario passar um cargo")
	private Role role;
	
	private String organizationName;
}

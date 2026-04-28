package com.lms.api.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.api.entity.Role;
import com.lms.api.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterResponseDTO {

	private UUID id;
	private String name;
	private String email;
	private Role role;
	private String organizationName;
	
	public static RegisterResponseDTO from(User user) {
		RegisterResponseDTO.RegisterResponseDTOBuilder buildResponse = RegisterResponseDTO.builder()
		.id(user.getId())
		.email(user.getEmail())
		.name(user.getName())
		.role(user.getRole());
		
		if(user.getOrganization() != null)
			buildResponse.organizationName(user.getOrganization().getName());
		
		return buildResponse.build();
	}
}

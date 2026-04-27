package com.lms.api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms.api.dto.LoginRequestDTO;
import com.lms.api.dto.LoginResponseDTO;
import com.lms.api.dto.RegisterRequestDTO;
import com.lms.api.entity.Organization;
import com.lms.api.entity.Role;
import com.lms.api.entity.User;
import com.lms.api.exception.BusinessException;
import com.lms.api.infra.security.JwtService;
import com.lms.api.infra.security.userdetails.CustomUserDetails;
import com.lms.api.repository.OrganizationRepository;
import com.lms.api.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final OrganizationRepository organizationRepository;
	private final AuthenticationManager authManager;
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;

	
	public LoginResponseDTO login(LoginRequestDTO loginRequest) {
		UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
		Authentication auth = authManager.authenticate(usernamePassword);
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		String token = jwtService.generateToken(userDetails);
		
		return LoginResponseDTO.builder()
				.token(token)
				.build();
	}
	
	@Transactional
	public void register(RegisterRequestDTO registerRequest) {
		if(userRepository.existsByEmail(registerRequest.getEmail())) {
			throw new BusinessException("Email já está em uso");
		}
		
		User user = userRepository.save(buildUser(registerRequest));
		
		if (registerRequest.getRole() == Role.INSTRUCTOR && registerRequest.getOrganizationName() != null) {
	        String orgName = registerRequest.getOrganizationName();
	        
	        Organization organization = organizationRepository.findByName(orgName)
	                .orElseGet(() -> {
	                    Organization newOrg = Organization.builder()
	                            .name(orgName)
	                            .build();
	                    return organizationRepository.save(newOrg);
	                });

	        user.setOrganization(organization);
	    }
		userRepository.save(user);
	}
	
	private User buildUser(RegisterRequestDTO registerRequestDTO) {
		return User.builder()
				.name(registerRequestDTO.getName())
				.email(registerRequestDTO.getEmail())
				.password(passwordEncoder.encode(registerRequestDTO.getPassword()))
				.role(registerRequestDTO.getRole())
				.build();
				
	}

}

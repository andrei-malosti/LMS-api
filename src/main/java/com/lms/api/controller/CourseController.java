package com.lms.api.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.api.dto.CourseRegisterDTO;
import com.lms.api.infra.multitenancy.OrganizationContext;
import com.lms.api.infra.multitenancy.UserContext;
import com.lms.api.service.CourseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

	private final CourseService courseService;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid CourseRegisterDTO registerDTO){
		return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(registerDTO, UserContext.getUserId(), OrganizationContext.getOrganizationId()));
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<?> findAllInstructorCourses(Pageable pageable){
		return ResponseEntity.ok(courseService.findUsersCourses(UserContext.getUserId(), OrganizationContext.getOrganizationId(), pageable));
	}
	
}

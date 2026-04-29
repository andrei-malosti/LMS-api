package com.lms.api.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.api.infra.multitenancy.UserContext;
import com.lms.api.service.EnrollmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

	private final EnrollmentService enrollmentService;
	
	@PostMapping("/course/{courseId}")
	public ResponseEntity<?> create(@PathVariable UUID courseId){
		return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.create(UserContext.getUserId(), courseId));
	}
	
	@GetMapping("/course/{courseId}")
	public ResponseEntity<?> findCourseEnrollments(@PathVariable UUID courseId, Pageable pageable){
		return ResponseEntity.ok(enrollmentService.findCourseEnrollments(UserContext.getUserId(), courseId, pageable));
	}
	
}

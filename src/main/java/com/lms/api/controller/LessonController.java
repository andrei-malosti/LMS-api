package com.lms.api.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.api.dto.LessonRegisterDTO;
import com.lms.api.infra.multitenancy.UserContext;
import com.lms.api.service.LessonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {

	private final LessonService lessonService;
	
	@PostMapping("/course/{courseId}")
	public ResponseEntity<?> create(@RequestBody @Valid LessonRegisterDTO lessonRegister, @PathVariable UUID courseId){
		return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.create(lessonRegister, courseId, UserContext.getUserId()));
	}
	
	@GetMapping("/course/{courseId}")
	public ResponseEntity<?> findCourseLessons(@PathVariable UUID courseId, Pageable pageable){
		return ResponseEntity.ok(lessonService.findCourseLessons(courseId, pageable));
	}
	
}

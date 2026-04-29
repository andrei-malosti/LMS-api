package com.lms.api.service;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.lms.api.dto.EnrollmentResponseDTO;
import com.lms.api.entity.Course;
import com.lms.api.entity.Enrollment;
import com.lms.api.entity.User;
import com.lms.api.exception.BusinessException;
import com.lms.api.repository.CourseRepository;
import com.lms.api.repository.EnrollmentRepository;
import com.lms.api.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

	private final EnrollmentRepository enrollmentRepository;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public EnrollmentResponseDTO create(UUID userId, UUID courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new BusinessException("Curso não encontrado"));
		
		if(course.getInstructor().getId().equals(userId))
			throw new BusinessException("O instrutor não pode se matricular em seu propio curso");
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new BusinessException("Usuario não encontrado"));
		
		return EnrollmentResponseDTO.from(enrollmentRepository.save(buildEnrollment(user, course)));
	}
	
	public Slice<EnrollmentResponseDTO> findCourseEnrollments(UUID instructorId, UUID courseId, Pageable pageable){
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new BusinessException("Curso não encontrado"));
		
		if(!course.getInstructor().getId().equals(instructorId))
			throw new BusinessException("Somente o instrutor do curso pode ver as matriculas de seu curso");
			
		return enrollmentRepository.findCourseEnrollments(courseId, pageable)
				.map(EnrollmentResponseDTO::from);
	}
	
	private Enrollment buildEnrollment(User user, Course course) {
		return Enrollment.builder()
				.user(user)
				.course(course)
				.enrolledAt(OffsetDateTime.now())
				.build();
}
	
	
}

package com.lms.api.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.lms.api.dto.LessonNoEnrollResponseDTO;
import com.lms.api.dto.LessonRegisterDTO;
import com.lms.api.dto.LessonResponseDTO;
import com.lms.api.entity.Course;
import com.lms.api.entity.Enrollment;
import com.lms.api.entity.Lesson;
import com.lms.api.exception.BusinessException;
import com.lms.api.repository.CourseRepository;
import com.lms.api.repository.EnrollmentRepository;
import com.lms.api.repository.LessonRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonService {

	private final LessonRepository lessonRepository;
	private final CourseRepository courseRepository;
	private final EnrollmentRepository enrollmentRepository;
	
	@Transactional
	public LessonResponseDTO create(LessonRegisterDTO lessonRegister, UUID courseId, UUID instructorId) {
		Lesson lesson = buildLesson(lessonRegister);
		Course course = courseRepository.findCourseByInstructorId(courseId, instructorId)
				.orElseThrow(() -> new BusinessException("Curso não encontrado"));
		lesson.setCourse(course);
		return LessonResponseDTO.from(lessonRepository.save(lesson));
	}
	
	@Transactional
	public LessonResponseDTO desactive(UUID lessonId, UUID userId) {
		Lesson lesson = lessonRepository.findLessonToEdit(userId, lessonId)
				.orElseThrow(() -> new BusinessException("Lição não encontrada"));
		lesson.setIsActive(false);
		return LessonResponseDTO.from(lesson);
	}
	
	@Transactional
	public LessonResponseDTO active(UUID lessonId, UUID userId) {
		Lesson lesson = lessonRepository.findLessonToEdit(userId, lessonId)
				.orElseThrow(() -> new BusinessException("Lição não encontrada"));
		lesson.setIsActive(true);
		return LessonResponseDTO.from(lesson);
	}
	
	public Slice<LessonResponseDTO> findCourseLessons(UUID courseId, UUID userId, Pageable pageable){
		Enrollment enrollment = enrollmentRepository.findEnrollment(courseId, userId)
				.orElse(null);
		
		if(enrollment != null && enrollment.getIsActive() == true) {
			return lessonRepository.findCourseLessons(courseId, pageable)
					.map(LessonResponseDTO::from);
		}
		return lessonRepository.findCourseLessons(courseId, pageable)
				.map(LessonNoEnrollResponseDTO::from);
	}
	
	private Lesson buildLesson(LessonRegisterDTO lessonRegister) {
		return Lesson.builder()
				.title(lessonRegister.getTitle())
				.content(lessonRegister.getContent())
				.isActive(true)
				.build();
	}
	
}

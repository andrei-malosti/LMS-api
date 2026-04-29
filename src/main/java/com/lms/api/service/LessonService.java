package com.lms.api.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.lms.api.dto.LessonRegisterDTO;
import com.lms.api.dto.LessonResponseDTO;
import com.lms.api.entity.Course;
import com.lms.api.entity.Lesson;
import com.lms.api.exception.BusinessException;
import com.lms.api.repository.CourseRepository;
import com.lms.api.repository.LessonRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonService {

	private final LessonRepository lessonRepository;
	private final CourseRepository courseRepository;
	
	@Transactional
	public LessonResponseDTO create(LessonRegisterDTO lessonRegister, UUID courseId, UUID instructorId) {
		Lesson lesson = buildLesson(lessonRegister);
		Course course = courseRepository.findCourseByInstructorId(courseId, instructorId)
				.orElseThrow(() -> new BusinessException("Curso não encontrado"));
		lesson.setCourse(course);
		return LessonResponseDTO.from(lessonRepository.save(lesson));
	}
	
	/*toDo validation that only persons who are enrolled will be able to see the content other persons that have no permission will only see the title
	and the instructor*/
	public Slice<LessonResponseDTO> findCourseLessons(UUID courseId, Pageable pageable){
		return lessonRepository.findCourseLessons(courseId, pageable)
				.map(LessonResponseDTO::from);
	}
	
	private Lesson buildLesson(LessonRegisterDTO lessonRegister) {
		return Lesson.builder()
				.title(lessonRegister.getTitle())
				.content(lessonRegister.getContent())
				.build();
	}
	
}

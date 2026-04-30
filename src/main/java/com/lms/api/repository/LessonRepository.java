package com.lms.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.api.entity.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID>{
	
	@Query("SELECT l FROM Lesson l WHERE l.course.id = :courseId AND l.isActive = true")
	Slice<Lesson> findCourseLessons(UUID courseId, Pageable pageable);
	
	@Query("SELECT l FROM Lesson l WHERE l.course.instructor.id = :instructorId AND l.id = :id")
	Optional<Lesson> findLessonToEdit(UUID instructorId, UUID id);

}

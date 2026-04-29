package com.lms.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.api.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID>{

	@Query("SELECT c FROM Course c WHERE c.instructor.id = :userId " +
		       "AND (:orgId IS NULL OR c.organization.id = :orgId)")
	public Slice<Course> findInstructorCourses(UUID userId, UUID orgId, Pageable pageable);
	
	@Query("SELECT c FROM Course c WHERE c.id = :id AND c.organization.id = :orgId AND c.instructor.id = :instructorId")
	public Optional<Course> findOrganizationCourse(UUID id ,UUID orgId, UUID instructorId);
	
	@Query("SELECT c FROM Course c WHERE c.id = :id AND c.instructor.id = :instructorId")
	public Optional<Course> findCourseByInstructorId(UUID id, UUID instructorId);
	
}

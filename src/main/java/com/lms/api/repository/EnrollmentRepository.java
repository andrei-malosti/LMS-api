package com.lms.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.api.entity.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID>{
	
	@Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId")
	public Slice<Enrollment> findCourseEnrollments(UUID courseId, Pageable pageable);
	
	@Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId AND e.user.id = :userId")
	public Optional<Enrollment> findEnrollment(UUID courseId, UUID userId);

}

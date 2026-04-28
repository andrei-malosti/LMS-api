package com.lms.api.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.lms.api.dto.CourseRegisterDTO;
import com.lms.api.dto.CourseResponseDTO;
import com.lms.api.entity.Course;
import com.lms.api.entity.Organization;
import com.lms.api.entity.Role;
import com.lms.api.entity.User;
import com.lms.api.exception.BusinessException;
import com.lms.api.repository.CourseRepository;
import com.lms.api.repository.OrganizationRepository;
import com.lms.api.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	private final OrganizationRepository organizationRepository;
	
	@Transactional
	public CourseResponseDTO create(CourseRegisterDTO courseRegister, UUID userId, UUID organizationId) {
		User user = userRepository.findByUUID(Role.INSTRUCTOR, organizationId)
				.orElseThrow(() -> new BusinessException("Instrutor não encontrado"));
		Organization organization = organizationRepository.findById(organizationId)
				.orElseThrow(() -> new BusinessException("Organização não encontrado"));
		
		Course courseToSave = buildCourse(courseRegister);
		courseToSave.setInstructor(user);
		courseToSave.setOrganization(organization);
		
		return CourseResponseDTO.from(courseRepository.save(courseToSave));
	}
	
	public Slice<CourseResponseDTO> findUsersCourses(UUID userId, UUID organizationId, Pageable pageable){
			return courseRepository.findInstructorCourses(userId, organizationId, pageable)
					.map(CourseResponseDTO::from);	
	}
	
	public Course buildCourse(CourseRegisterDTO courseRegister) {
		Course.CourseBuilder builder = Course.builder()
				.title(courseRegister.getTitle());
		
		if(courseRegister.getDescription() != null)
			builder.description(courseRegister.getDescription());
		
		return builder.build();
	}
	
}

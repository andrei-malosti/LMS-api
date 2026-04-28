package com.lms.api.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.api.entity.Course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseResponseDTO {

	private UUID id;
	private String title;
	private String description;
	private String instructorName;
	private String organizationName;
	
	public static CourseResponseDTO from(Course course) {
		return CourseResponseDTO.builder()
				.id(course.getId())
				.title(course.getTitle())
				.description(course.getDescription())
				.instructorName(course.getInstructor().getName())
				.organizationName(course.getOrganization().getName())
				.build();
	}
	
}

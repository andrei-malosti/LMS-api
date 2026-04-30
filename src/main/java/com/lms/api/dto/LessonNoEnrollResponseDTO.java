package com.lms.api.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.api.entity.Lesson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonNoEnrollResponseDTO extends LessonResponseDTO{

	private UUID id;
	private String title;
	private String instructorName;
	private String courseTitle;
	private Boolean isActive;
	
	public static LessonResponseDTO from(Lesson lesson) {
		return LessonResponseDTO.builder()
				.id(lesson.getId())
				.title(lesson.getTitle())
				.instructorName(lesson.getCourse().getInstructor().getName())
				.courseTitle(lesson.getCourse().getTitle())
				.isActive(lesson.getIsActive())
				.build();
	}
	
}

package com.lms.api.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.api.entity.Lesson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonResponseDTO {

	private UUID id;
	private String title;
	private String content;
	private String instructorName;
	private String courseTitle;
	
	public static LessonResponseDTO from(Lesson lesson) {
		return LessonResponseDTO.builder()
				.id(lesson.getId())
				.title(lesson.getTitle())
				.content(lesson.getContent())
				.instructorName(lesson.getCourse().getInstructor().getName())
				.courseTitle(lesson.getCourse().getTitle())
				.build();
	}
	
}

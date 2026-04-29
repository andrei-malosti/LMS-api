package com.lms.api.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.lms.api.entity.Enrollment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponseDTO {

	private UUID enrollmentId;
	private UUID userId;
	private UUID courseId;
	private OffsetDateTime enrolledAt;
	
	public static EnrollmentResponseDTO from(Enrollment enroll) {
		return EnrollmentResponseDTO.builder()
				.enrollmentId(enroll.getId())
				.userId(enroll.getUser().getId())
				.courseId(enroll.getCourse().getId())
				.enrolledAt(enroll.getEnrolledAt())
				.build();
	}
	
}

package com.lms.api.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Course course;
	
	@CreationTimestamp
	@Column(nullable = false ,updatable = false)
	private OffsetDateTime enrolledAt;

}

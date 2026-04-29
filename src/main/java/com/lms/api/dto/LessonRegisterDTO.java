package com.lms.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonRegisterDTO {

	@NotBlank(message = "Titulo da lição é obrigatorio")
	private String title;
	
	@NotBlank(message = "Conteudo da lição é obrigatorio")
	private String content;
	
}

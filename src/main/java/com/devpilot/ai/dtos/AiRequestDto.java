package com.devpilot.ai.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Step 1
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiRequestDto {
	
	private String question;

}

package com.devpilot.ai.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devpilot.ai.aiservices.AiService;
import com.devpilot.ai.dtos.AiRequestDto;


@RestController
@RequestMapping("/ai")
public class AiController {
	private final AiService aiService;
	
	public AiController(AiService aiService) {
		this.aiService = aiService;
	}
	
//	@GetMapping("/ask")
//	public String askAi(@RequestParam String question) {
//		return aiService.askAi(question);
//	}
	
	//Step 2 
	@PostMapping("/ask")
	public String askAi(@RequestBody AiRequestDto aiRequestDto) {
		return aiService.askAi(aiRequestDto.getQuestion());
	}

}

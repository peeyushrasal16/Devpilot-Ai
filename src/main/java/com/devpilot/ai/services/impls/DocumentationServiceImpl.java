package com.devpilot.ai.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpilot.ai.dtos.DocumentationDto;
import com.devpilot.ai.entities.Documentation;
import com.devpilot.ai.repositories.DocumentationRepository;
import com.devpilot.ai.services.DocumentationService;

@Service
public class DocumentationServiceImpl implements DocumentationService{
	
	@Autowired
	private DocumentationRepository documentationRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public DocumentationDto createDocumentation(DocumentationDto documentationDto) {
		  Documentation documentation = modelMapper.map(documentationDto, Documentation.class);
		  
		  documentation.setCreatedAt(java.time.LocalDateTime.now());
		  documentation.setUpdatedAt(java.time.LocalDateTime.now());

	      Documentation savedDocumentation = documentationRepository.save(documentation);

	      return modelMapper.map(savedDocumentation, DocumentationDto.class);
	}

	
	@Override
	public DocumentationDto updateDocumentation(Long id, DocumentationDto documentationDto) {

        Documentation documentation = documentationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documentation not found"));

        documentation.setTitle(documentationDto.getTitle());
        documentation.setContent(documentationDto.getContent());
        documentation.setType(documentationDto.getType());

        Documentation updatedDocumentation = documentationRepository.save(documentation);

        return modelMapper.map(updatedDocumentation, DocumentationDto.class);
	}

	
	@Override
	public DocumentationDto getDocumentationById(Long id) {
		
		Documentation documentation = documentationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documentation not found"));

        return modelMapper.map(documentation, DocumentationDto.class);
	}

	
	@Override
	public List<DocumentationDto> getAllDocumentations() {
	    List<Documentation> documentations = documentationRepository.findAll();

        return documentations.stream()
                .map(documentation -> modelMapper.map(documentation, DocumentationDto.class))
                .collect(Collectors.toList());
	}

	@Override
	public void deleteDocumentation(Long id) {
		Documentation documentation = documentationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documentation not found"));

        documentationRepository.delete(documentation);
		
	}

}

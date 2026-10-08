package com.devpilot.ai.services;

import java.util.List;
import com.devpilot.ai.dtos.DocumentationDto;

public interface DocumentationService {

    DocumentationDto createDocumentation(DocumentationDto documentationDto);

    DocumentationDto updateDocumentation(Long id, DocumentationDto documentationDto);

    DocumentationDto getDocumentationById(Long id);

    List<DocumentationDto> getAllDocumentations();

    void deleteDocumentation(Long id);
}
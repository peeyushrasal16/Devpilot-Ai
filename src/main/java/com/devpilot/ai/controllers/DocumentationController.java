package com.devpilot.ai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpilot.ai.dtos.DocumentationDto;
import com.devpilot.ai.services.DocumentationService;

@RestController
@RequestMapping("/documentation")
public class DocumentationController {

    @Autowired
    private DocumentationService documentationService;

    @PostMapping
    public ResponseEntity<DocumentationDto> createDocumentation(
            @RequestBody DocumentationDto documentationDto) {

        DocumentationDto savedDocumentation =
                documentationService.createDocumentation(documentationDto);

        return new ResponseEntity<DocumentationDto>(
                savedDocumentation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentationDto> updateDocumentation(
            @PathVariable Long id,
            @RequestBody DocumentationDto documentationDto) {

        DocumentationDto updatedDocumentation =
                documentationService.updateDocumentation(id, documentationDto);

        return ResponseEntity.ok(updatedDocumentation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentationDto> getDocumentationById(
            @PathVariable Long id) {

        DocumentationDto documentation =
                documentationService.getDocumentationById(id);

        return ResponseEntity.ok(documentation);
    }

    @GetMapping
    public ResponseEntity<List<DocumentationDto>> getAllDocumentations() {

        List<DocumentationDto> documentations =
                documentationService.getAllDocumentations();

        return ResponseEntity.ok(documentations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentation(
            @PathVariable Long id) {

        documentationService.deleteDocumentation(id);

        return ResponseEntity.noContent().build();
    }
}
package com.devpilot.ai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpilot.ai.dtos.BugDto;
import com.devpilot.ai.services.BugService;

@RestController
@RequestMapping("/bugs")
public class BugController {

    @Autowired
    private BugService bugService;

    @PostMapping
    public ResponseEntity<BugDto> createBug(@RequestBody BugDto bugDto) {

        BugDto savedBug = bugService.createBug(bugDto);

        return new ResponseEntity<BugDto>(savedBug, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BugDto> updateBug(
            @PathVariable Long id,
            @RequestBody BugDto bugDto) {

        BugDto updatedBug = bugService.updateBug(id, bugDto);

        return ResponseEntity.ok(updatedBug);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BugDto> getBugById(@PathVariable Long id) {

        BugDto bug = bugService.getBugById(id);

        return ResponseEntity.ok(bug);
    }

    @GetMapping
    public ResponseEntity<List<BugDto>> getAllBugs() {

        List<BugDto> bugs = bugService.getAllBugs();

        return ResponseEntity.ok(bugs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBug(@PathVariable Long id) {

        bugService.deleteBug(id);

        return ResponseEntity.noContent().build();
    }
}
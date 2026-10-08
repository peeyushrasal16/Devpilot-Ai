package com.devpilot.ai.services;

import java.util.List;
import com.devpilot.ai.dtos.BugDto;

public interface BugService {

    BugDto createBug(BugDto bugDto);

    BugDto updateBug(Long id, BugDto bugDto);

    BugDto getBugById(Long id);

    List<BugDto> getAllBugs();

    void deleteBug(Long id);
}
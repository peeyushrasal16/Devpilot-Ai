package com.devpilot.ai.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpilot.ai.dtos.BugDto;
import com.devpilot.ai.entities.Bug;
import com.devpilot.ai.repositories.BugRepository;
import com.devpilot.ai.services.BugService;

@Service
public class BugServiceImpl implements BugService{
	
	@Autowired
	private BugRepository bugRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public BugDto createBug(BugDto bugDto) {
		
		 Bug bug = modelMapper.map(bugDto, Bug.class);
		 
		 bug.setCreatedAt(java.time.LocalDateTime.now());
		 bug.setUpdatedAt(java.time.LocalDateTime.now());

	     Bug savedBug = bugRepository.save(bug);

	     return modelMapper.map(savedBug, BugDto.class);
	}

	
	@Override
	public BugDto updateBug(Long id, BugDto bugDto) {
		
		Bug bug = bugRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bug not found"));

        bug.setTitle(bugDto.getTitle());
        bug.setDescription(bugDto.getDescription());
        bug.setStatus(bugDto.getStatus());
        bug.setPriority(bugDto.getPriority());
        bug.setStepsToReproduce(bugDto.getStepsToReproduce());
        bug.setExpectedResult(bugDto.getExpectedResult());
        bug.setActualResult(bugDto.getActualResult());

        Bug updatedBug = bugRepository.save(bug);

        return modelMapper.map(updatedBug, BugDto.class);
	}

	
	@Override
	public BugDto getBugById(Long id) {
		
		Bug bug = bugRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bug not found"));

        return modelMapper.map(bug, BugDto.class);
	}

	
	@Override
	public List<BugDto> getAllBugs() {
		 List<Bug> bugs = bugRepository.findAll();

	        return bugs.stream()
	                .map(bug -> modelMapper.map(bug, BugDto.class))
	                .collect(Collectors.toList());
	}

	@Override
	public void deleteBug(Long id) {
		Bug bug = bugRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bug not found"));

        bugRepository.delete(bug);
		
	}

}

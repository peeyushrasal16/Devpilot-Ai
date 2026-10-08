package com.devpilot.ai.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devpilot.ai.dtos.UserDto;
import com.devpilot.ai.entities.User;
import com.devpilot.ai.repositories.UserRepository;
import com.devpilot.ai.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto registerUser(UserDto userDto) {
		
		 if (userRepository.existsByEmail(userDto.getEmail())) {
		        throw new RuntimeException("Email already exists");
		    }
		
		 if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
		        throw new RuntimeException("Passwords do not match");
		    }
		 
		 User user = modelMapper.map(userDto, User.class);
		 
		 user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		 
		 user.setCreatedAt(java.time.LocalDateTime.now());
		 user.setUpdatedAt(java.time.LocalDateTime.now());

	     User savedUser = userRepository.save(user);

	     return modelMapper.map(savedUser, UserDto.class);
	     
	}

}

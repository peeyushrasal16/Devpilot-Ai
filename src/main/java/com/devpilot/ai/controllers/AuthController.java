package com.devpilot.ai.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.devpilot.ai.dtos.UserDto;
import com.devpilot.ai.entities.User;
import com.devpilot.ai.security.LoginRequest;
import com.devpilot.ai.security.LoginResponse;
import com.devpilot.ai.security.jwt.JwtUtils;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        try {

            Authentication authenticate =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequest.getEmail(),
                                    loginRequest.getPassword()
                            )
                    );

            SecurityContextHolder.getContext().setAuthentication(authenticate);

            User user = (User) authenticate.getPrincipal();

            String token = jwtUtils.generateTokenFromUsername(user);

            UserDto userDto = modelMapper.map(user, UserDto.class);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setUserDto(userDto);

            return new ResponseEntity<>(loginResponse, HttpStatus.OK);

        } catch (BadCredentialsException e) {

            return new ResponseEntity<>(
                    "Invalid email or password",
                    HttpStatus.UNAUTHORIZED
            );
        }
    }
}



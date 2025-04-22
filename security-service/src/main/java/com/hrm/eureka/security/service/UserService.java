package com.hrm.eureka.security.service;

import com.hrm.eureka.security.dto.UserDto;
import com.hrm.eureka.security.dto.request.LoginRequestDto;
import com.hrm.eureka.security.dto.request.RegisterRequestDto;
import com.hrm.eureka.security.dto.response.LoginResponseDto;
import com.hrm.eureka.security.mapper.UserMapper;
import com.hrm.eureka.security.model.User;
import com.hrm.eureka.security.principal.UserPrincipal;
import com.hrm.eureka.security.repository.UserRepository;
import com.hrm.eureka.security.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        // After this line, Spring looks for a UserDetailsService bean to load the user
//        System.out.println(loginRequestDto.getUsername());
//        System.out.println(loginRequestDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

//        System.out.println(authentication.getPrincipal());

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = jwtUtils.generateToken(userPrincipal);
        User user = userRepository.findByUsername(userPrincipal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new LoginResponseDto(accessToken, UserMapper.toUserDto(user));
    }

    public UserDto registerUser(RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByUsername(registerRequestDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setFirstName(registerRequestDto.getFirstName());
        user.setLastName(registerRequestDto.getLastName());
        user.setEmail(registerRequestDto.getEmail());
//        user.setRole("USER");

        User savedUser = userRepository.save(user);
        return UserMapper.toUserDto(savedUser);
    }
}

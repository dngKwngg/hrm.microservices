package com.hrm.eureka.common.service;

import com.hrm.eureka.common.constants.ResponseCode;
import com.hrm.eureka.common.constants.RoleType;
import com.hrm.eureka.common.dto.CommonResponse;
import com.hrm.eureka.common.dto.UserDto;
import com.hrm.eureka.common.dto.request.LoginRequestDto;
import com.hrm.eureka.common.dto.request.RegisterRequestDto;
import com.hrm.eureka.common.dto.response.LoginResponseDto;
import com.hrm.eureka.common.mapper.UserMapper;
import com.hrm.eureka.common.model.Role;
import com.hrm.eureka.common.model.User;
import com.hrm.eureka.common.principal.UserPrincipal;
import com.hrm.eureka.common.repository.RoleRepository;
import com.hrm.eureka.common.repository.UserRepository;
import com.hrm.eureka.common.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
    }

    public CommonResponse loginUser(LoginRequestDto loginRequestDto) {
        log.info("[Common Service] Login Request: {}", loginRequestDto);
        // After this line, Spring looks for a UserDetailsService bean to load the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = jwtUtils.generateToken(userPrincipal);
        Optional<User> optionalUser = userRepository.findByUsername(userPrincipal.getUsername());
        if (optionalUser.isEmpty()) {
            log.error("[Common Service] User not found");
            throw new IllegalArgumentException("User not found");
        }
        User user = optionalUser.get();

        return new CommonResponse(ResponseCode.SUCCESS, new LoginResponseDto(accessToken, UserMapper.toUserDto(user)));
    }

    public CommonResponse registerUser(RegisterRequestDto registerRequestDto) {
        log.info("[Common Service] Register Request: {}", registerRequestDto);
        if (userRepository.existsByUsername(registerRequestDto.getUsername())) {
            log.error("[Common Service] Username already exists");
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            log.error("[Common Service] Email already exists");
            throw new IllegalArgumentException("Email already exists");
        }

        Role role = roleRepository.findByRoleName(RoleType.USER);

        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setFirstName(registerRequestDto.getFirstName());
        user.setLastName(registerRequestDto.getLastName());
        user.setEmail(registerRequestDto.getEmail());
        user.setRole(role);

        User savedUser = userRepository.save(user);

        return new CommonResponse(ResponseCode.CREATED, UserMapper.toUserDto(savedUser));
    }
}

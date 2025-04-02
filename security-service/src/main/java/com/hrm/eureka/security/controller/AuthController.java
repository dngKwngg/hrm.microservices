package com.hrm.eureka.security.controller;

import com.hrm.eureka.security.dto.request.LoginRequestDto;
import com.hrm.eureka.security.dto.response.LoginResponseDto;
import com.hrm.eureka.security.mapper.UserMapper;
import com.hrm.eureka.security.model.User;
import com.hrm.eureka.security.principal.UserPrincipal;
import com.hrm.eureka.security.repository.UserRepository;
import com.hrm.eureka.security.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
            );

            System.out.println(authentication.getPrincipal());

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//            System.out.println(userPrincipal);
            String accessToken = jwtUtils.generateToken(userPrincipal);
            User user = userRepository.findByUsername(userPrincipal.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

            return ResponseEntity.ok(new LoginResponseDto(accessToken, UserMapper.toUserDto(user)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

}

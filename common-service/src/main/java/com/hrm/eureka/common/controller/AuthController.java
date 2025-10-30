package com.hrm.eureka.common.controller;

import com.hrm.eureka.common.dto.UserDto;
import com.hrm.eureka.common.dto.request.LoginRequestDto;
import com.hrm.eureka.common.dto.request.RegisterRequestDto;
import com.hrm.eureka.common.dto.response.LoginResponseDto;
import com.hrm.eureka.common.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        log.info("[Common Service] POST /api/v1/auth/login");
        try {
            LoginResponseDto loginResponseDto = userService.loginUser(loginRequestDto);
            return ResponseEntity.ok(loginResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        log.info("[Common Service] POST /api/v1/auth/register");
        UserDto userDto = userService.registerUser(registerRequestDto);
        return ResponseEntity.ok(userDto);
    }
}

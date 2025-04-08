package com.hrm.eureka.security.controller;

import com.hrm.eureka.security.dto.UserDto;
import com.hrm.eureka.security.dto.request.LoginRequestDto;
import com.hrm.eureka.security.dto.request.RegisterRequestDto;
import com.hrm.eureka.security.dto.response.LoginResponseDto;
import com.hrm.eureka.security.mapper.UserMapper;
import com.hrm.eureka.security.model.User;
import com.hrm.eureka.security.principal.UserPrincipal;
import com.hrm.eureka.security.repository.UserRepository;
import com.hrm.eureka.security.service.UserService;
import com.hrm.eureka.security.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            LoginResponseDto loginResponseDto = userService.loginUser(loginRequestDto);
            return ResponseEntity.ok(loginResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto registerRequestDto) {
        UserDto userDto = userService.registerUser(registerRequestDto);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/login/google")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    @GetMapping("/login/success")
    public ResponseEntity<?> loginSuccess(@RequestParam String token) {
        return ResponseEntity.ok(Map.of("token", token));
    }
}

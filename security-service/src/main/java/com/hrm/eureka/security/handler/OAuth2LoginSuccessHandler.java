package com.hrm.eureka.security.handler;

import com.hrm.eureka.security.model.User;
import com.hrm.eureka.security.principal.UserPrincipal;
import com.hrm.eureka.security.repository.UserRepository;
import com.hrm.eureka.security.utils.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        System.out.println("email: " + email);
        System.out.println("name: " + name);
        System.out.println("picture: " + picture);

        User user = userRepository.findByEmail(email).orElseGet(
                () -> {
                    User newUser = new User();
                    newUser.setUsername(email);
                    newUser.setEmail(email);
                    newUser.setFirstName(name);
                    newUser.setRole("USER");
                    return userRepository.save(newUser);
                }
        );

        // Generate JWT token
        // Create UserDetails object
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setUserId(user.getUserId());
        userPrincipal.setUsername(user.getUsername());
        userPrincipal.setPassword(user.getPassword());
        userPrincipal.setDepartmentId(1L);
        userPrincipal.setRole(user.getRole());
        userPrincipal.setAuthorities(authentication.getAuthorities());
        String token = jwtUtils.generateToken(userPrincipal);

        // Redirect with JWT in query param or set it as a cookie
        response.sendRedirect("http://localhost:8080/login/success?token=" + token);
    }
}

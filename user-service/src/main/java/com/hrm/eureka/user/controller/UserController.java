package com.hrm.eureka.user.controller;

import com.hrm.eureka.user.dto.UserDto;
import com.hrm.eureka.user.dto.request.UpdateUserRequest;
import com.hrm.eureka.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/department/{departmentId}")
    public List<UserDto> getUsersByDepartmentId(@PathVariable Long departmentId) {
        return userService.getUsersByDepartmentId(departmentId);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN' or 'USER')")
    @GetMapping("/me")
    public UserDto getCurrentUser(@RequestHeader("Authorization") String token) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(SecurityContextHolder.getContext().getAuthentication().toString());
//        System.out.println(username);
        return userService.getCurrentUser(username);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{userId}/assign-admin")
    public UserDto assignRoleAdmin(@PathVariable Long userId) {
        return userService.assignRoleAdmin(userId);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        return userService.updateUser(userId, request);
    }

    @PutMapping("/me")
    public UserDto updateCurrentUser(@RequestBody UpdateUserRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.updateCurrentUserInformation(username, request);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    // Add user to a department
    @PutMapping("/{userId}/department/{departmentId}")
    public UserDto addUserToDepartment(@PathVariable Long userId, @PathVariable Long departmentId) {
        return userService.addUserToDepartment(userId, departmentId);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}

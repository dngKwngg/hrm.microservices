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

    @PreAuthorize("hasAuthority('READ_ALL_USERS')")
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('READ_USERS_IN_DEPARTMENT')")
    @GetMapping("/department/{departmentId}")
    public List<UserDto> getUsersByDepartmentId(@PathVariable Long departmentId) {
        return userService.getUsersByDepartmentId(departmentId);
    }

    @PreAuthorize("hasAuthority('READ_USER_BY_USER_ID')")
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PreAuthorize("hasAuthority('READ_CURRENT_USER')")
    @GetMapping("/me")
    public UserDto getCurrentUser(@RequestHeader("Authorization") String token) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(SecurityContextHolder.getContext().getAuthentication().toString());
//        System.out.println(username);
        return userService.getCurrentUser(username);
    }

    @PreAuthorize("hasAuthority('ASSIGN_USER_AS_ADMIN')")
    @PutMapping("/{userId}/assign-admin")
    public UserDto assignRoleAdmin(@PathVariable Long userId) {
        return userService.assignRoleAdmin(userId);
    }

    @PreAuthorize("hasAuthority('UPDATE_USER_BY_USER_ID')")
    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        return userService.updateUser(userId, request);
    }

    @PreAuthorize("hasAuthority('UPDATE_CURRENT_USER')")
    @PutMapping("/me")
    public UserDto updateCurrentUser(@RequestBody UpdateUserRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.updateCurrentUserInformation(username, request);
    }

    @PreAuthorize("hasAuthority('ASSIGN_USER_TO_DEPARTMENT')")
    // Add user to a department
    @PutMapping("/{userId}/department/{departmentId}")
    public UserDto addUserToDepartment(@PathVariable Long userId, @PathVariable Long departmentId) {
        return userService.addUserToDepartment(userId, departmentId);
    }

    @PreAuthorize("hasAuthority('DELETE_USER_BY_USER_ID')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}

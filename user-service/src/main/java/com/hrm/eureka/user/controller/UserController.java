package com.hrm.eureka.user.controller;

import com.hrm.eureka.user.dto.UserDto;
import com.hrm.eureka.user.dto.request.UpdateUserRequest;
import com.hrm.eureka.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/department/{departmentId}")
    public List<UserDto> getUsersByDepartmentId(@PathVariable Long departmentId) {
        return userService.getUsersByDepartmentId(departmentId);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}/assign-admin")
    public UserDto assignRoleAdmin(@PathVariable Long userId) {
        return userService.assignRoleAdmin(userId);
    }

    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        return userService.updateUser(userId, request);
    }

    // Add user to a department
    @PutMapping("/{userId}/department/{departmentId}")
    public UserDto addUserToDepartment(@PathVariable Long userId, @PathVariable Long departmentId) {
        return userService.addUserToDepartment(userId, departmentId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}

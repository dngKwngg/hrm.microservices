package com.hrm.eureka.user.service;

import com.hrm.eureka.user.client.DepartmentClient;
import com.hrm.eureka.user.dto.UserDto;
import com.hrm.eureka.user.dto.request.CreateUserRequest;
import com.hrm.eureka.user.dto.request.UpdateUserRequest;
import com.hrm.eureka.user.mapper.UserMapper;
import com.hrm.eureka.user.model.User;
import com.hrm.eureka.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DepartmentClient departmentClient;

    public UserService(UserRepository userRepository, DepartmentClient departmentClient){
        this.userRepository = userRepository;
        this.departmentClient = departmentClient;
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersByDepartmentId(Long departmentId){
        if (!departmentClient.isDepartmentExist(departmentId)){
            throw new EntityNotFoundException("Department not found with id: " + departmentId);
        }
        return userRepository.findByDepartmentId(departmentId).stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long userId){
        return userRepository.findById(userId)
                .map(UserMapper::mapToUserDto)
                .orElse(null);
    }

    public UserDto createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Validate and set role safely

        if (!(request.getRole().equals("ADMIN") || request.getRole().equals("USER"))){
            throw new IllegalArgumentException("Invalid role. Allowed values: ADMIN, USER");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    public UserDto updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(updatedUser);
    }

    public UserDto addUserToDepartment(Long userId, Long departmentId) {
        if (!departmentClient.isDepartmentExist(departmentId)) {
            throw new EntityNotFoundException("Department not found with id: " + departmentId);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setDepartmentId(departmentId);
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}

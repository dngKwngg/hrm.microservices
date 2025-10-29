package com.hrm.eureka.user.service;

import com.hrm.eureka.user.dto.UserDto;
import com.hrm.eureka.user.dto.request.UpdateUserRequest;
import com.hrm.eureka.user.mapper.UserMapper;
import com.hrm.eureka.user.model.Department;
import com.hrm.eureka.user.model.User;
import com.hrm.eureka.user.repository.DepartmentRepository;
import com.hrm.eureka.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private static final Long ADMIN_ROLE_ID = 1L;

    public UserService(UserRepository userRepository, DepartmentRepository departmentRepository){
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<UserDto> getAllUsers(){
        log.info("[User Service] Getting all users");
        return userRepository.findAll().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersByDepartmentId(Long departmentId){
        log.info("[User Service] Getting users by department id");
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (optionalDepartment.isEmpty()) {
            log.error("Department with id {} not found", departmentId);
            throw new EntityNotFoundException("Department not found with id: " + departmentId);
        }

        return userRepository.findByDepartmentId(departmentId).stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long userId){
        log.info("[User Service] Getting user by id {}", userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.error("User with id {} not found", userId);
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return UserMapper.mapToUserDto(optionalUser.get());
    }

    public UserDto getCurrentUser(String username) {
        log.info("[User Service] Getting current user with username {}", username);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            log.error("User with username {} not found", username);
            throw new EntityNotFoundException("User not found with username: " + username);
        }
        return UserMapper.mapToUserDto(optionalUser.get());
    }

    public UserDto assignRoleAdmin(Long userId) {
        log.info("[User Service] Assigning role admin for user with id {}", userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.error("User with id {} not found", userId);
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        User user = optionalUser.get();
        user.setRoleId(ADMIN_ROLE_ID);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    public UserDto updateUser(Long userId, UpdateUserRequest request) {
        log.info("[User Service] Updating user with id {}", userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.error("User with id {} not found", userId);
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        User user = optionalUser.get();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(updatedUser);
    }

    public UserDto updateUser(String username, UpdateUserRequest request) {
        log.info("[User Service] Updating current user with username {}", username);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            log.error("User with username {} not found", username);
            throw new EntityNotFoundException("User not found with username: " + username);
        }
        User user = optionalUser.get();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(updatedUser);
    }

    public UserDto addUserToDepartment(Long userId, Long departmentId) {
        log.info("[User Service] Adding user with id {} to department with id {}", userId, departmentId);
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalDepartment.isEmpty()) {
            log.error("Department with id {} not found", departmentId);
            throw new EntityNotFoundException("Department not found with id: " + departmentId);
        }

        if (optionalUser.isEmpty()) {
            log.error("User with id {} not found", userId);
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        User user = optionalUser.get();
        user.setDepartmentId(departmentId);
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(updatedUser);
    }

    public void deleteUser(Long id) {
        log.info("[User Service] Deleting user with id {}", id);
        if (!userRepository.existsById(id)) {
            log.error("User with id {} not found", id);
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}

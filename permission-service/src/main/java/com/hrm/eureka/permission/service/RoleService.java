package com.hrm.eureka.permission.service;

import com.hrm.eureka.permission.dto.RoleDto;
import com.hrm.eureka.permission.mapper.RoleMapper;
import com.hrm.eureka.permission.model.Role;
import com.hrm.eureka.permission.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDto> getAllRoles() {
        // Implementation to fetch all roles from the database
        return roleRepository.findAll().stream()
                .map(RoleMapper::toRoleDto)
                .collect(Collectors.toList());
    }

    public RoleDto getRoleById(Long roleId) {
        // Implementation to fetch a role by ID from the database
        Role role = roleRepository.findById(Math.toIntExact(roleId))
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));

        return RoleMapper.toRoleDto(role);
    }
}

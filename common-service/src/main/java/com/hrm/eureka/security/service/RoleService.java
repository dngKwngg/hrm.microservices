package com.hrm.eureka.security.service;


import com.hrm.eureka.security.dto.RoleDto;
import com.hrm.eureka.security.mapper.RoleMapper;
import com.hrm.eureka.security.model.Role;
import com.hrm.eureka.security.repository.RoleRepository;
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

    public RoleDto createRole(RoleDto roleDto) {
        Role role = RoleMapper.toRole(roleDto);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.toRoleDto(savedRole);
    }

    public RoleDto updateRole(Long roleId, RoleDto roleDto) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        existingRole.setRoleName(roleDto.getRoleName());

        Role updatedRole = roleRepository.save(existingRole);
        return RoleMapper.toRoleDto(updatedRole);
    }

    public void deleteRole(Long roleId) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        roleRepository.delete(existingRole);
    }
}

package com.hrm.eureka.permission.service;

import com.hrm.eureka.permission.dto.RolePermissionDto;
import com.hrm.eureka.permission.mapper.RolePermissionMapper;
import com.hrm.eureka.permission.repository.RolePermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;

    public RolePermissionService(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public List<RolePermissionDto> getPermissionsByRoleId(Long roleId) {
        // Fetch permissions by role ID from the repository
        return rolePermissionRepository.findAllPermissionByRoleId(roleId).stream()
                .map(RolePermissionMapper::toRolePermissionDto)
                .collect(Collectors.toList());
    }
}

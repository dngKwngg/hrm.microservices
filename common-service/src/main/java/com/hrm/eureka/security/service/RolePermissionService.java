package com.hrm.eureka.security.service;

import com.hrm.eureka.security.dto.PermissionDto;
import com.hrm.eureka.security.mapper.RolePermissionMapper;
import com.hrm.eureka.security.repository.RolePermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;

    public RolePermissionService(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public List<PermissionDto> getPermissionsByRoleId(Long roleId) {
        // Fetch permissions by role ID from the repository
        return rolePermissionRepository.findAllPermissionByRoleId(roleId).stream()
                .map(RolePermissionMapper::toPermissionDto)
                .collect(Collectors.toList());
    }


}

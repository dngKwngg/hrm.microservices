package com.hrm.eureka.permission.service;

import com.hrm.eureka.permission.dto.PermissionDto;
import com.hrm.eureka.permission.mapper.PermissionMapper;
import com.hrm.eureka.permission.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<PermissionDto> getAllPermissions() {
        // Implementation to fetch all permissions from the database
        return permissionRepository.findAll().stream()
                .map(PermissionMapper::toPermissionDto)
                .collect(Collectors.toList());
    }
}

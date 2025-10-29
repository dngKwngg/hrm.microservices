package com.hrm.eureka.security.service;

import com.hrm.eureka.security.dto.PermissionDto;
import com.hrm.eureka.security.mapper.PermissionMapper;
import com.hrm.eureka.security.model.Permission;
import com.hrm.eureka.security.repository.PermissionRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public PermissionDto createPermission(PermissionDto permissionDto) {
        Permission permission = PermissionMapper.toPermission(permissionDto);
        Permission savedPermission = permissionRepository.save(permission);
        return PermissionMapper.toPermissionDto(savedPermission);
    }

    public PermissionDto updatePermission(Long permissionId, PermissionDto permissionDto) {
        Permission existingPermission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found"));
        existingPermission.setPermissionName(permissionDto.getPermissionName());

        Permission updatedPermission = permissionRepository.save(existingPermission);
        return PermissionMapper.toPermissionDto(updatedPermission);
    }

    public void deletePermission(Long permissionId) {
        Permission existingPermission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found"));
        permissionRepository.delete(existingPermission);
    }
}

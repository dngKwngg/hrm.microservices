package com.hrm.eureka.common.service;

import com.hrm.eureka.common.dto.PermissionDto;
import com.hrm.eureka.common.mapper.PermissionMapper;
import com.hrm.eureka.common.model.Permission;
import com.hrm.eureka.common.repository.PermissionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    private static final Logger log = LoggerFactory.getLogger(PermissionService.class);
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<PermissionDto> getAllPermissions() {
        log.info("[Common Service] Getting all permissions");
        return permissionRepository.findAll().stream()
                .map(PermissionMapper::toPermissionDto)
                .collect(Collectors.toList());
    }

    public PermissionDto createPermission(PermissionDto permissionDto) {
        log.info("[Common Service] Creating a new permission");
        Permission permission = PermissionMapper.toPermission(permissionDto);
        Permission savedPermission = permissionRepository.save(permission);
        return PermissionMapper.toPermissionDto(savedPermission);
    }

    public PermissionDto updatePermission(Long permissionId, PermissionDto permissionDto) {
        log.info("[Common Service] Updating permission with ID: {}", permissionId);
        Optional<Permission> optionalPermission = permissionRepository.findById(permissionId);
        if (optionalPermission.isEmpty()) {
            log.error("[Common Service] Permission not found");
            throw new EntityNotFoundException("Permission not found");
        }
        Permission permission = optionalPermission.get();
        permission.setPermissionName(permissionDto.getPermissionName());

        Permission updatedPermission = permissionRepository.save(permission);
        return PermissionMapper.toPermissionDto(updatedPermission);
    }

    public void deletePermission(Long permissionId) {
        log.info("[Common Service] Deleting permission with ID: {}", permissionId);
        if (!permissionRepository.existsById(permissionId)) {
            log.error("[Common Service] Permission not found");
            throw new EntityNotFoundException("Permission not found");
        }
        permissionRepository.deleteById(permissionId);
    }
}

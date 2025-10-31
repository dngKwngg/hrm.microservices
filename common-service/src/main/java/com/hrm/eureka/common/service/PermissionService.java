package com.hrm.eureka.common.service;

import com.hrm.eureka.common.constants.ResponseCode;
import com.hrm.eureka.common.dto.CommonResponse;
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

    public CommonResponse getAllPermissions() {
        log.info("[Common Service] Getting all permissions");
        List<String> permissions = permissionRepository.findAll().stream()
                .map(Permission::getPermissionName)
                .toList();

        return new CommonResponse(ResponseCode.SUCCESS, permissions);
    }

    public CommonResponse createPermission(PermissionDto permissionDto) {
        log.info("[Common Service] Creating a new permission");
        Permission permission = PermissionMapper.toPermission(permissionDto);
        Permission savedPermission = permissionRepository.save(permission);
        return new CommonResponse(ResponseCode.CREATED, savedPermission);
    }

    public CommonResponse updatePermission(Long permissionId, PermissionDto permissionDto) {
        log.info("[Common Service] Updating permission with ID: {}", permissionId);
        Optional<Permission> optionalPermission = permissionRepository.findById(permissionId);
        if (optionalPermission.isEmpty()) {
            log.error("[Common Service] Permission not found");
            throw new EntityNotFoundException("Permission not found");
        }
        Permission permission = optionalPermission.get();
        permission.setPermissionName(permissionDto.getPermissionName());

        Permission updatedPermission = permissionRepository.save(permission);
        return new CommonResponse(ResponseCode.SUCCESS, PermissionMapper.toPermissionDto(updatedPermission));
    }

    public CommonResponse deletePermission(Long permissionId) {
        log.info("[Common Service] Deleting permission with ID: {}", permissionId);
        if (!permissionRepository.existsById(permissionId)) {
            log.error("[Common Service] Permission not found");
            throw new EntityNotFoundException("Permission not found");
        }
        permissionRepository.deleteById(permissionId);
        return new CommonResponse(ResponseCode.SUCCESS, "");
    }
}

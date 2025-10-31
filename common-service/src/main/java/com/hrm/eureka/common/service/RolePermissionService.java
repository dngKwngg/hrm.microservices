package com.hrm.eureka.common.service;

import com.hrm.eureka.common.constants.ResponseCode;
import com.hrm.eureka.common.dto.CommonResponse;
import com.hrm.eureka.common.dto.PermissionDto;
import com.hrm.eureka.common.dto.request.RolePermissionRequestDto;
import com.hrm.eureka.common.dto.response.RolePermissionResponseDto;
import com.hrm.eureka.common.mapper.RolePermissionMapper;
import com.hrm.eureka.common.model.Permission;
import com.hrm.eureka.common.model.Role;
import com.hrm.eureka.common.model.RolePermission;
import com.hrm.eureka.common.model.RolePermissionID;
import com.hrm.eureka.common.repository.PermissionRepository;
import com.hrm.eureka.common.repository.RolePermissionRepository;
import com.hrm.eureka.common.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolePermissionService {
    private static final Logger log = LoggerFactory.getLogger(RolePermissionService.class);
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RolePermissionService(RolePermissionRepository rolePermissionRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public CommonResponse getPermissionsByRoleId(Long roleId) {
        log.info("[Common Service] Getting permissions for role ID: {}", roleId);
        List<String> permissions =  rolePermissionRepository.findAllPermissionByRoleId(roleId).stream()
                .map(rolePermission -> rolePermission.getPermission().getPermissionName())
                .toList();

        return new CommonResponse(ResponseCode.SUCCESS, permissions);
    }

    public CommonResponse assignPermissionsForRole(RolePermissionRequestDto dto) {
        log.info("[Common Service] Assigning permissions for role ID: {}", dto.getRoleId());
        Optional<Role> optionalRole = roleRepository.findById(dto.getRoleId());
        if (optionalRole.isEmpty()) {
            log.error("[Common Service] Role not found");
            throw new EntityNotFoundException("Role not found");
        }

        Optional<Permission> optionalPermission = permissionRepository.findById(dto.getPermissionId());
        if (optionalPermission.isEmpty()) {
            log.error("[Common Service] Permission not found");
            throw new EntityNotFoundException("Permission not found");
        }

        Optional<RolePermission> optionalRolePermission = rolePermissionRepository.findById(new RolePermissionID(dto.getRoleId(), dto.getPermissionId()));
        if (optionalRolePermission.isPresent()) {
            log.error("[Common Service] Permission already assigned to role");
            throw new IllegalArgumentException("Permission already assigned to role");
        }

        RolePermission rolePermission = RolePermissionMapper.toRolePermission(dto);
        rolePermission.setRole(optionalRole.get());
        rolePermission.setPermission(optionalPermission.get());

        RolePermission savedRolePermission = rolePermissionRepository.save(rolePermission);
        return new CommonResponse(ResponseCode.CREATED, RolePermissionMapper.toRolePermissionDto(savedRolePermission));
    }

}

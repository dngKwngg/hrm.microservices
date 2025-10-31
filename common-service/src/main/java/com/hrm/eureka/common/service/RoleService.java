package com.hrm.eureka.common.service;


import com.hrm.eureka.common.constants.ResponseCode;
import com.hrm.eureka.common.dto.CommonResponse;
import com.hrm.eureka.common.dto.PermissionDto;
import com.hrm.eureka.common.dto.RoleDto;
import com.hrm.eureka.common.mapper.RoleMapper;
import com.hrm.eureka.common.mapper.RolePermissionMapper;
import com.hrm.eureka.common.model.Role;
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
public class RoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleService.class);
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public RoleService(RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository) {
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public CommonResponse getAllRoles() {
        log.info("[Common Service] Getting all roles");
        List<RoleDto> roleDtos = roleRepository.findAll().stream()
                .map(role -> {
                    RoleDto roleDto = RoleMapper.toRoleDto(role);
                    List<String> permissions = rolePermissionRepository.findAllPermissionByRoleId(role.getRoleId()).stream()
                                    .map(rolePermission -> rolePermission.getPermission().getPermissionName())
                                    .toList();
                    roleDto.setPermissions(permissions);
                    return roleDto;
                })
                .toList();

        return new CommonResponse(ResponseCode.SUCCESS, roleDtos);
    }
}

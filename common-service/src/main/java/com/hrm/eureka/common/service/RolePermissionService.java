package com.hrm.eureka.common.service;

import com.hrm.eureka.common.constants.ResponseCode;
import com.hrm.eureka.common.dto.CommonResponse;
import com.hrm.eureka.common.dto.PermissionDto;
import com.hrm.eureka.common.mapper.RolePermissionMapper;
import com.hrm.eureka.common.repository.RolePermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermissionService {
    private static final Logger log = LoggerFactory.getLogger(RolePermissionService.class);
    private final RolePermissionRepository rolePermissionRepository;

    public RolePermissionService(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public CommonResponse getPermissionsByRoleId(Long roleId) {
        log.info("[Common Service] Getting permissions for role ID: {}", roleId);
        List<String> permissions =  rolePermissionRepository.findAllPermissionByRoleId(roleId).stream()
                .map(rolePermission -> rolePermission.getPermission().getPermissionName())
                .toList();

        return new CommonResponse(ResponseCode.SUCCESS, permissions);
    }


}

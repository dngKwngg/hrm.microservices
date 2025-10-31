package com.hrm.eureka.common.controller;

import com.hrm.eureka.common.dto.CommonResponse;
import com.hrm.eureka.common.dto.request.RolePermissionRequestDto;
import com.hrm.eureka.common.service.RolePermissionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-permission")
public class RolePermissionController {
    private static final Logger log = LoggerFactory.getLogger(RolePermissionController.class);
    private final RolePermissionService rolePermissionService;

    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @PreAuthorize("hasAuthority('GET_PERMISSIONS_BY_ROLE')")
    @GetMapping("/role/{roleId}/permissions")
    public CommonResponse getPermissionsByRoleId(@PathVariable Long roleId) {
        log.info("[Common Service] GET /api/v1/role-permission/role/{}/permissions", roleId);
        return rolePermissionService.getPermissionsByRoleId(roleId);
    }

    @PreAuthorize("hasAuthority('ASSIGN_PERMISSION_FOR_ROLE')")
    @PostMapping()
    public CommonResponse assignPermissionsForRole(@Valid @RequestBody RolePermissionRequestDto dto) {
        log.info("[Common Service] POST /api/v1/role-permission");
        return rolePermissionService.assignPermissionsForRole(dto);
    }
}

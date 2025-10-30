package com.hrm.eureka.common.controller;

import com.hrm.eureka.common.dto.PermissionDto;
import com.hrm.eureka.common.service.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<String> getPermissionsByRoleId(@PathVariable Long roleId) {
        log.info("[Common Service] GET /api/v1/role-permission/role/{}/permissions", roleId);
        return rolePermissionService.getPermissionsByRoleId(roleId);
    }
}

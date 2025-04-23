package com.hrm.eureka.security.controller;

import com.hrm.eureka.security.dto.PermissionDto;
import com.hrm.eureka.security.service.RolePermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-permission")
public class RolePermissionController {
    private final RolePermissionService rolePermissionService;

    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping("/role/{roleId}/permissions")
    public List<PermissionDto> getPermissionsByRoleId(@PathVariable Long roleId) {
        return rolePermissionService.getPermissionsByRoleId(roleId);
    }
}

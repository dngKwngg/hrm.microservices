package com.hrm.eureka.permission.controller;

import com.hrm.eureka.permission.dto.RolePermissionDto;
import com.hrm.eureka.permission.service.RolePermissionService;
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

    @GetMapping("role/{roleId}/permissions")
    public List<RolePermissionDto> getPermissionsByRoleId(@PathVariable Long roleId) {
        // Fetch permissions by role ID from the service
        return rolePermissionService.getPermissionsByRoleId(roleId);
    }
}

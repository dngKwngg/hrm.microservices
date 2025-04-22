package com.hrm.eureka.permission.controller;

import com.hrm.eureka.permission.dto.RoleDto;
import com.hrm.eureka.permission.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-permission")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public List<RoleDto> getAllRoles() {
        // Assuming roleService is a service that fetches roles from the database
        return roleService.getAllRoles();
    }

    @GetMapping("/roles/{roleId}")
    public RoleDto getRoleById(@PathVariable Long roleId) {
        // Fetch role by ID from the service
        return roleService.getRoleById(roleId);
    }
}

package com.hrm.eureka.permission.controller;

import com.hrm.eureka.permission.dto.PermissionDto;
import com.hrm.eureka.permission.service.PermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-permission")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/permissions")
    public List<PermissionDto> getAllPermissions() {
        // Assuming permissionService is a service that fetches permissions from the database
        return permissionService.getAllPermissions();
    }
}

package com.hrm.eureka.common.controller;

import com.hrm.eureka.common.dto.PermissionDto;
import com.hrm.eureka.common.service.PermissionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-permission")
public class PermissionController {
    private static final Logger log = LoggerFactory.getLogger(PermissionController.class);
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PreAuthorize("hasAuthority('READ_ALL_PERMISSIONS')")
    @GetMapping("/permissions")
    public List<PermissionDto> getAllPermissions() {
        log.info("[Common Service] GET /api/v1/role-permission/permissions");
        // Assuming permissionService is a service that fetches permissions from the database
        return permissionService.getAllPermissions();
    }

    @PreAuthorize("hasAuthority('CREATE_PERMISSION')")
    @PostMapping("/permissions")
    public PermissionDto createPermission(@Valid @RequestBody PermissionDto permissionDto) {
        log.info("[Common Service] POST /api/v1/role-permission/permissions");
        return permissionService.createPermission(permissionDto);
    }

    @PreAuthorize("hasAuthority('UPDATE_PERMISSION')")
    @PutMapping("/permissions/{permissionId}")
    public PermissionDto updatePermission(@PathVariable Long permissionId, @Valid @RequestBody PermissionDto permissionDto) {
        log.info("[Common Service] PUT /api/v1/role-permission/permissions/{}", permissionId);
        return permissionService.updatePermission(permissionId, permissionDto);
    }

    @PreAuthorize("hasAuthority('DELETE_PERMISSION')")
    @DeleteMapping("/permissions/{permissionId}")
    public ResponseEntity<?> deletePermission(@PathVariable Long permissionId) {
        log.info("[Common Service] DELETE /api/v1/role-permission/permissions/{}", permissionId);
        permissionService.deletePermission(permissionId);
        return ResponseEntity.ok("Deleted successfully");
    }
}

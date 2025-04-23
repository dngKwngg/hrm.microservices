package com.hrm.eureka.security.controller;

import com.hrm.eureka.security.dto.PermissionDto;
import com.hrm.eureka.security.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-permission")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PreAuthorize("hasAuthority('READ_ALL_PERMISSIONS')")
    @GetMapping("/permissions")
    public List<PermissionDto> getAllPermissions() {
        // Assuming permissionService is a service that fetches permissions from the database
        return permissionService.getAllPermissions();
    }

    @PreAuthorize("hasAuthority('CREATE_PERMISSION')")
    @PostMapping("/permissions")
    public PermissionDto createPermission(@RequestBody PermissionDto permissionDto) {
        return permissionService.createPermission(permissionDto);
    }

    @PreAuthorize("hasAuthority('UPDATE_PERMISSION')")
    @PutMapping("/permissions/{permissionId}")
    public PermissionDto updatePermission(@PathVariable Long permissionId, @RequestBody PermissionDto permissionDto) {
        return permissionService.updatePermission(permissionId, permissionDto);
    }

    @PreAuthorize("hasAuthority('DELETE_PERMISSION')")
    @DeleteMapping("/permissions/{permissionId}")
    public ResponseEntity<?> deletePermission(@PathVariable Long permissionId) {
        permissionService.deletePermission(permissionId);
        return ResponseEntity.ok("Deleted successfully");
    }
}

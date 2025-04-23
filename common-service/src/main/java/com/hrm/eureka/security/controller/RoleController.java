package com.hrm.eureka.security.controller;

import com.hrm.eureka.security.dto.RoleDto;
import com.hrm.eureka.security.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-permission")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasAuthority('READ_ALL_ROLES')")
    @GetMapping("/roles")
    public List<RoleDto> getAllRoles() {
        // Assuming roleService is a service that fetches roles from the database
        return roleService.getAllRoles();
    }

    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    @PostMapping("/roles")
    public RoleDto createRole(@RequestBody RoleDto roleDto) {
        return roleService.createRole(roleDto);
    }

    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    @PutMapping("/roles/{roleId}")
    public RoleDto updateRole(@PathVariable Long roleId, @RequestBody RoleDto roleDto) {
        return roleService.updateRole(roleId, roleDto);
    }

    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.ok("Deleted successfully");
    }


}

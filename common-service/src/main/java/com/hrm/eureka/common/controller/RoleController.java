package com.hrm.eureka.common.controller;

import com.hrm.eureka.common.dto.RoleDto;
import com.hrm.eureka.common.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-permission")
public class RoleController {
    private static final Logger log = LoggerFactory.getLogger(RoleController.class);
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasAuthority('READ_ALL_ROLES')")
    @GetMapping("/roles")
    public List<RoleDto> getAllRoles() {
        log.info("[Common Service] GET /api/v1/role-permission/roles");
        return roleService.getAllRoles();
    }

    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    @PostMapping("/roles")
    public RoleDto createRole(@RequestBody RoleDto roleDto) {
        log.info("[Common Service] POST /api/v1/role-permission/roles");
        return roleService.createRole(roleDto);
    }

    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    @PutMapping("/roles/{roleId}")
    public RoleDto updateRole(@PathVariable Long roleId, @RequestBody RoleDto roleDto) {
        log.info("[Common Service] PUT /api/v1/role-permission/roles/{}", roleId);
        return roleService.updateRole(roleId, roleDto);
    }

    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable Long roleId) {
        log.info("[Common Service] DELETE /api/v1/role-permission/roles/{}", roleId);
        roleService.deleteRole(roleId);
        return ResponseEntity.ok("Deleted successfully");
    }


}

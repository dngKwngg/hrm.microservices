package com.hrm.eureka.common.controller;

import com.hrm.eureka.common.dto.CommonResponse;
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
    public CommonResponse getAllRoles() {
        log.info("[Common Service] GET /api/v1/role-permission/roles");
        return roleService.getAllRoles();
    }
}

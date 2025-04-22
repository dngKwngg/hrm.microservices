package com.hrm.eureka.security.client;

import com.hrm.eureka.permission.dto.RoleDto;
import com.hrm.eureka.permission.dto.RolePermissionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "permission-service")
public interface PermissionClient {

    @GetMapping("/api/v1/role-permission/role/{roleId}/permissions")
    List<RolePermissionDto> getPermissionsByRoleId(@PathVariable Long roleId);

    @GetMapping("/api/v1/role-permission/roles/{roleId}")
    RoleDto getRoleById(@PathVariable Long roleId);
}

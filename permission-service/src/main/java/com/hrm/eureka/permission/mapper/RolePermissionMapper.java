package com.hrm.eureka.permission.mapper;

import com.hrm.eureka.permission.dto.PermissionDto;
import com.hrm.eureka.permission.dto.RoleDto;
import com.hrm.eureka.permission.dto.RolePermissionDto;
import com.hrm.eureka.permission.model.RolePermission;

public class RolePermissionMapper {
    public static RolePermissionDto toRolePermissionDto(RolePermission rolePermission) {
        if (rolePermission == null) {
            return null;
        }
        RolePermissionDto rolePermissionDto = new RolePermissionDto();
        rolePermissionDto.setRoleId(rolePermission.getRole().getRoleId());
        rolePermissionDto.setRoleName(rolePermission.getRole().getRoleName());
        rolePermissionDto.setPermissionId(rolePermission.getPermission().getPermissionId());
        rolePermissionDto.setPermissionName(rolePermission.getPermission().getPermissionName());
        return rolePermissionDto;
    }

    public static RolePermission toRolePermission(RolePermissionDto rolePermissionDto) {
        if (rolePermissionDto == null) {
            return null;
        }
        RolePermission rolePermission = new RolePermission();

        RoleDto roleDto = new RoleDto(rolePermissionDto.getRoleId(), rolePermissionDto.getRoleName());
        rolePermission.setRole(new RoleMapper().toRole(roleDto));

        PermissionDto permissionDto = new PermissionDto(rolePermissionDto.getPermissionId(), rolePermissionDto.getPermissionName());
        rolePermission.setPermission(new PermissionMapper().toPermission(permissionDto));

        return rolePermission;
    }
}

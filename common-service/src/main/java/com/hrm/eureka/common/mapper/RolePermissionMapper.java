package com.hrm.eureka.common.mapper;

import com.hrm.eureka.common.dto.PermissionDto;
import com.hrm.eureka.common.dto.response.RolePermissionResponseDto;
import com.hrm.eureka.common.model.RolePermission;

public class RolePermissionMapper {
    public static RolePermissionResponseDto toRolePermissionDto(RolePermission rolePermission) {
        if (rolePermission == null) {
            return null;
        }
        RolePermissionResponseDto rolePermissionResponseDto = new RolePermissionResponseDto();
        rolePermissionResponseDto.setRoleName(rolePermission.getRole().getRoleName());
        rolePermissionResponseDto.setPermissionName(rolePermission.getPermission().getPermissionName());
        return rolePermissionResponseDto;
    }

    public static PermissionDto toPermissionDto(RolePermission rolePermission) {
        if (rolePermission == null) {
            return null;
        }
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setPermissionId(rolePermission.getPermission().getPermissionId());
        permissionDto.setPermissionName(rolePermission.getPermission().getPermissionName());
        return permissionDto;
    }
}

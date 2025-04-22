package com.hrm.eureka.permission.mapper;

import com.hrm.eureka.permission.dto.PermissionDto;
import com.hrm.eureka.permission.model.Permission;

public class PermissionMapper {
    public static PermissionDto toPermissionDto(Permission permission) {
        if (permission == null) {
            return null;
        }
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setPermissionId(permission.getPermissionId());
        permissionDto.setPermissionName(permission.getPermissionName());
        return permissionDto;
    }

    public static Permission toPermission(PermissionDto permissionDto) {
        if (permissionDto == null) {
            return null;
        }
        Permission permission = new Permission();
        permission.setPermissionId(permissionDto.getPermissionId());
        permission.setPermissionName(permissionDto.getPermissionName());
        return permission;
    }
}

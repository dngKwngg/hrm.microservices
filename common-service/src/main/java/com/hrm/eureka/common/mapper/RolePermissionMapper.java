package com.hrm.eureka.common.mapper;

import com.hrm.eureka.common.dto.PermissionDto;
import com.hrm.eureka.common.dto.request.RolePermissionRequestDto;
import com.hrm.eureka.common.dto.response.RolePermissionResponseDto;
import com.hrm.eureka.common.model.RolePermission;
import com.hrm.eureka.common.model.RolePermissionID;

public class RolePermissionMapper {
    public static RolePermissionResponseDto toRolePermissionDto(RolePermission rolePermission) {
        if (rolePermission == null) {
            return null;
        }
        RolePermissionResponseDto rolePermissionResponseDto = new RolePermissionResponseDto();
        rolePermissionResponseDto.setRoleName(String.valueOf(rolePermission.getRole().getRoleName()));
        rolePermissionResponseDto.setPermissionName(rolePermission.getPermission().getPermissionName());
        return rolePermissionResponseDto;
    }

    public static PermissionDto toPermissionDto(RolePermission rolePermission) {
        if (rolePermission == null) {
            return null;
        }
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setPermissionName(rolePermission.getPermission().getPermissionName());
        return permissionDto;
    }

    public static RolePermission toRolePermission(RolePermissionRequestDto dto) {
        if (dto == null) {
            return null;
        }
        RolePermission rolePermission = new RolePermission();
        rolePermission.setId(new RolePermissionID(dto.getRoleId(), dto.getPermissionId()));
        return rolePermission;
    }
}

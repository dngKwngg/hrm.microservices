package com.hrm.eureka.security.mapper;


import com.hrm.eureka.security.dto.PermissionDto;
import com.hrm.eureka.security.dto.RoleDto;
import com.hrm.eureka.security.dto.request.RolePermissionRequestDto;
import com.hrm.eureka.security.dto.response.RolePermissionResponseDto;
import com.hrm.eureka.security.model.RolePermission;

public class RolePermissionMapper {
    public static RolePermissionResponseDto toRolePermissionDto(RolePermission rolePermission) {
        if (rolePermission == null) {
            return null;
        }
        RolePermissionResponseDto rolePermissionResponseDto = new RolePermissionResponseDto();
//        rolePermissionRequestDto.setRoleId(rolePermission.getRole().getRoleId());
        rolePermissionResponseDto.setRoleName(rolePermission.getRole().getRoleName());
//        rolePermissionRequestDto.setPermissionId(rolePermission.getPermission().getPermissionId());
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

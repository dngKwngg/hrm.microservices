package com.hrm.eureka.common.mapper;

import com.hrm.eureka.common.dto.RoleDto;
import com.hrm.eureka.common.model.Role;

public class RoleMapper {
    public static RoleDto toRoleDto(Role role) {
        if (role == null) {
            return null;
        }
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(role.getRoleId());
        roleDto.setRoleName(role.getRoleName());
        return roleDto;
    }

    public static Role toRole(RoleDto roleDto) {
        if (roleDto == null) {
            return null;
        }
        Role role = new Role();
        role.setRoleId(roleDto.getRoleId());
        role.setRoleName(roleDto.getRoleName());
        return role;
    }
}

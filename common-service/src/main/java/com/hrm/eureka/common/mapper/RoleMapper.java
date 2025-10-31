package com.hrm.eureka.common.mapper;

import com.hrm.eureka.common.dto.RoleDto;
import com.hrm.eureka.common.model.Role;

public class RoleMapper {
    public static RoleDto toRoleDto(Role role) {
        if (role == null) {
            return null;
        }
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName(role.getRoleName());
        return roleDto;
    }
}

package com.hrm.eureka.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionDto {
    private Long roleId;
    private String roleName;
    private Long permissionId;
    private String permissionName;
}

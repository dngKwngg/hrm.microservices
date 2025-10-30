package com.hrm.eureka.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionResponseDto {
    private String roleName;
    private String permissionName;
}

package com.hrm.eureka.security.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionRequestDto {
    private Long roleId;
    private Long permissionId;
}

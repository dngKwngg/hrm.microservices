package com.hrm.eureka.common.dto.request;

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

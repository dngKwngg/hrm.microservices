package com.hrm.eureka.common.dto;

import com.hrm.eureka.common.constants.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long roleId;
    private RoleType roleName;
    private List<String> permissions;
}

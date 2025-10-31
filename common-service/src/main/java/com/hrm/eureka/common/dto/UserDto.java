package com.hrm.eureka.common.dto;

import com.hrm.eureka.common.constants.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private RoleType roleName;
    private String departmentName;

}

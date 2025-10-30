package com.hrm.eureka.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String roleName;
    private Long departmentId;

}

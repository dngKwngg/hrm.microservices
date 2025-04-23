package com.hrm.eureka.security.mapper;

import com.hrm.eureka.security.dto.UserDto;
import com.hrm.eureka.security.model.Role;
import com.hrm.eureka.security.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(user.getUserId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole().getRoleName(), user.getDepartmentId());
    }
}

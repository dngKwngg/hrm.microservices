package com.hrm.eureka.common.mapper;

import com.hrm.eureka.common.dto.UserDto;
import com.hrm.eureka.common.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(user.getUserId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole().getRoleName(), user.getDepartmentId());
    }
}

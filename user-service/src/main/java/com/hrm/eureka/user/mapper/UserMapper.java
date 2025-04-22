package com.hrm.eureka.user.mapper;

import com.hrm.eureka.user.dto.UserDto;
import com.hrm.eureka.user.model.User;

public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRoleId(),
                user.getDepartmentId(),
                user.isActive()
        );
    }

    public static User mapToUser(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setRoleId(userDto.getRoleId());
        user.setDepartmentId(userDto.getDepartmentId());
        user.setActive(userDto.isActive());

        return user;
    }
}

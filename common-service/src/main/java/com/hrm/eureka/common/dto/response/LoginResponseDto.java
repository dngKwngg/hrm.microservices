package com.hrm.eureka.common.dto.response;

import com.hrm.eureka.common.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String token;
    private UserDto user;
}

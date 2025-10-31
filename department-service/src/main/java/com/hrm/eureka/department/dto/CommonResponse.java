package com.hrm.eureka.department.dto;

import com.hrm.eureka.department.constants.ResponseCode;
import lombok.Data;

@Data
public class CommonResponse {
    private Integer code;
    private String message;
    private Object data;

    public CommonResponse(ResponseCode code, Object data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }
}


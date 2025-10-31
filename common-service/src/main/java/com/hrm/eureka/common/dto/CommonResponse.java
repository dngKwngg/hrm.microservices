package com.hrm.eureka.common.dto;

import com.hrm.eureka.common.constants.ResponseCode;
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

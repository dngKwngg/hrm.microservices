package com.hrm.eureka.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gateway-server")
public interface DepartmentClient {

    @GetMapping("/api/v1/departments/is-exist/{departmentId}")
    boolean isDepartmentExist(@PathVariable Long departmentId);
}

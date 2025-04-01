package com.hrm.eureka.department.mapper;

import com.hrm.eureka.department.dto.DepartmentDto;
import com.hrm.eureka.department.model.Department;

public class DepartmentMapper {
    public static Department mapToDepartment(DepartmentDto dto){
        Department department = new Department();
        department.setDepartmentId(dto.getDepartmentId());
        department.setDepartmentName(dto.getDepartmentName());
        department.setDescription(dto.getDescription());
        department.setManagerId(dto.getManagerId());

        return department;
    }

    public static DepartmentDto mapToDepartmentDto(Department department){
        DepartmentDto dto = new DepartmentDto();
        dto.setDepartmentId(department.getDepartmentId());
        dto.setDepartmentName(department.getDepartmentName());
        dto.setDescription(department.getDescription());
        dto.setManagerId(department.getManagerId());

        return dto;
    }
}

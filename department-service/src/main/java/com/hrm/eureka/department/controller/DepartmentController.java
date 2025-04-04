package com.hrm.eureka.department.controller;

import com.hrm.eureka.department.dto.DepartmentDto;
import com.hrm.eureka.department.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<DepartmentDto> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{departmentId}")
    public DepartmentDto getDepartmentById(@PathVariable Long departmentId){
        return departmentService.getDepartmentById(departmentId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto createDepartment(@RequestBody DepartmentDto departmentDto){
        return departmentService.createDepartment(departmentDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{departmentId}")
    public DepartmentDto updateDepartment(@PathVariable Long departmentId, @RequestBody DepartmentDto departmentDto){
        return departmentService.updateDepartment(departmentId, departmentDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long departmentId){
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok("Department deleted successfully");
    }

    @GetMapping("/is-exist/{departmentId}")
    public boolean isDepartmentExist(@PathVariable Long departmentId){
        return departmentService.isDepartmentExist(departmentId);
    }
}

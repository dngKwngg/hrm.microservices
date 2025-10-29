package com.hrm.eureka.department.controller;

import com.hrm.eureka.department.dto.DepartmentDto;
import com.hrm.eureka.department.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {
    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PreAuthorize("hasAuthority('READ_ALL_DEPARTMENTS')")
    @GetMapping
    public List<DepartmentDto> getAllDepartments(){
        log.info("[Department Service] GET /api/v1/departments");
        return departmentService.getAllDepartments();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{departmentId}")
    public DepartmentDto getDepartmentById(@PathVariable Long departmentId){
        log.info("[Department Service] GET /api/v1/departments/{}", departmentId);
        return departmentService.getDepartmentById(departmentId);
    }

    @PreAuthorize("hasAuthority('CREATE_DEPARTMENT')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto createDepartment(@RequestBody DepartmentDto departmentDto){
        log.info("[Department Service] POST /api/v1/departments");
        return departmentService.createDepartment(departmentDto);
    }

    @PreAuthorize("hasAuthority('UPDATE_DEPARTMENT')")
    @PutMapping("/{departmentId}")
    public DepartmentDto updateDepartment(@PathVariable Long departmentId, @RequestBody DepartmentDto departmentDto){
        log.info("[Department Service] PUT /api/v1/departments/{}", departmentId);
        return departmentService.updateDepartment(departmentId, departmentDto);
    }

    @PreAuthorize("hasAuthority('DELETE_DEPARTMENT')")
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long departmentId){
        log.info("[Department Service] DELETE /api/v1/departments/{}", departmentId);
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok("Department deleted successfully");
    }

    @GetMapping("/is-exist/{departmentId}")
    public boolean isDepartmentExist(@PathVariable Long departmentId){
        return departmentService.isDepartmentExist(departmentId);
    }
}

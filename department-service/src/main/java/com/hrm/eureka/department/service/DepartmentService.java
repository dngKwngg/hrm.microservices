package com.hrm.eureka.department.service;

import com.hrm.eureka.department.dto.DepartmentDto;
import com.hrm.eureka.department.mapper.DepartmentMapper;
import com.hrm.eureka.department.model.Department;
import com.hrm.eureka.department.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDto> getAllDepartments(){
        log.info("[Department Service] Getting all departments");
        return departmentRepository.findAll().stream()
                .map(DepartmentMapper::mapToDepartmentDto)
                .collect(Collectors.toList());
    }

    public DepartmentDto getDepartmentById(Long departmentId){
        log.info("[Department Service] Getting department by id {}", departmentId);

        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (optionalDepartment.isEmpty()) {
            log.error("Department with id {} not found", departmentId);
            throw new EntityNotFoundException("Department not found with id: " + departmentId);
        }
        return DepartmentMapper.mapToDepartmentDto(optionalDepartment.get());
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDto){
        log.info("[Department Service] Creating department");
        Department department = new Department();
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDescription(departmentDto.getDescription());

        return DepartmentMapper.mapToDepartmentDto(departmentRepository.save(department));
    }

    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto){
        log.info("[Department Service] Updating department with id {}", departmentId);
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (optionalDepartment.isEmpty()) {
            log.error("Department with id {} not found", departmentId);
            throw new EntityNotFoundException("Department not found with id: " + departmentId);
        }

        Department department = optionalDepartment.get();
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDescription(departmentDto.getDescription());

        return DepartmentMapper.mapToDepartmentDto(departmentRepository.save(department));
    }

    public void deleteDepartment(Long departmentId){
        log.info("[Department Service] Deleting department with id {}", departmentId);
        if (!departmentRepository.existsById(departmentId)) {
            log.error("Department with id {} not found", departmentId);
            throw new EntityNotFoundException("Department not found with id: " + departmentId);
        }
        departmentRepository.deleteById(departmentId);
    }

    public boolean isDepartmentExist(Long departmentId){
        return departmentRepository.existsById(departmentId);
    }
}

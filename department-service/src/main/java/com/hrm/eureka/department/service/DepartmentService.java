package com.hrm.eureka.department.service;

import com.hrm.eureka.department.dto.DepartmentDto;
import com.hrm.eureka.department.mapper.DepartmentMapper;
import com.hrm.eureka.department.model.Department;
import com.hrm.eureka.department.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDto> getAllDepartments(){
        return departmentRepository.findAll().stream()
                .map(DepartmentMapper::mapToDepartmentDto)
                .collect(Collectors.toList());
    }

    public DepartmentDto getDepartmentById(Long departmentId){
        return departmentRepository.findById(departmentId)
                .map(DepartmentMapper::mapToDepartmentDto)
                // Throw exception if department not found
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + departmentId));
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDto){
        Department department = new Department();
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDescription(departmentDto.getDescription());

        return DepartmentMapper.mapToDepartmentDto(departmentRepository.save(department));
    }

    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto){
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + departmentId));

        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDescription(departmentDto.getDescription());
//        department.setManagerId(departmentDto.getManagerId());

        return DepartmentMapper.mapToDepartmentDto(departmentRepository.save(department));
    }

    public void deleteDepartment(Long departmentId){
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + departmentId));
        departmentRepository.deleteById(departmentId);
    }

    public boolean isDepartmentExist(Long departmentId){
        return departmentRepository.existsById(departmentId);
    }
}

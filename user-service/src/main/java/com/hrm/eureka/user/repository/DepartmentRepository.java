package com.hrm.eureka.user.repository;

import com.hrm.eureka.user.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}

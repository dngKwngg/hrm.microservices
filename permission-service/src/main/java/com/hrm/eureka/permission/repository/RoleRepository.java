package com.hrm.eureka.permission.repository;

import com.hrm.eureka.permission.dto.RoleDto;
import com.hrm.eureka.permission.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}

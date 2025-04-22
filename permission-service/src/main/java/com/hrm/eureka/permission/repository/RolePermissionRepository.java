package com.hrm.eureka.permission.repository;

import com.hrm.eureka.permission.model.RolePermission;
import com.hrm.eureka.permission.model.RolePermissionID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionID> {
    // Get all permissions by role ID
    @Query("SELECT rp FROM RolePermission rp WHERE rp.id.roleId = :roleId")
    List<RolePermission> findAllPermissionByRoleId(@Param("roleId") Long roleId);
}

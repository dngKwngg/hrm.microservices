package com.hrm.eureka.security.repository;

import com.hrm.eureka.security.model.RolePermission;
import com.hrm.eureka.security.model.RolePermissionID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionID> {
    @Query("SELECT rp FROM RolePermission rp WHERE rp.id.roleId = :roleId")
    List<RolePermission> findAllPermissionByRoleId(@Param("roleId") Long roleId);
}

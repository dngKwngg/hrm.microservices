package com.hrm.eureka.common.service;


import com.hrm.eureka.common.dto.RoleDto;
import com.hrm.eureka.common.mapper.RoleMapper;
import com.hrm.eureka.common.model.Role;
import com.hrm.eureka.common.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleService.class);
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDto> getAllRoles() {
        log.info("[Common Service] Getting all roles");
        return roleRepository.findAll().stream()
                .map(RoleMapper::toRoleDto)
                .collect(Collectors.toList());
    }

    public RoleDto createRole(RoleDto roleDto) {
        log.info("[Common Service] Creating role");
        Role role = RoleMapper.toRole(roleDto);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.toRoleDto(savedRole);
    }

    public RoleDto updateRole(Long roleId, RoleDto roleDto) {
        log.info("[Common Service] Updating role for ID: {}", roleId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty()) {
            log.error("[Common Service] Role not found");
            throw new EntityNotFoundException("Role not found");
        }
        Role role = optionalRole.get();
        role.setRoleName(roleDto.getRoleName());

        Role updatedRole = roleRepository.save(role);
        return RoleMapper.toRoleDto(updatedRole);
    }

    public void deleteRole(Long roleId) {
        log.info("[Common Service] Deleting role for ID: {}", roleId);
        if (!roleRepository.existsById(roleId)) {
            log.error("[Common Service] Role not found");
            throw new EntityNotFoundException("Role not found");
        }
        roleRepository.deleteById(roleId);
    }
}

package com.hrm.eureka.common.service;

import com.hrm.eureka.common.dto.PermissionDto;
import com.hrm.eureka.common.dto.RoleDto;
import com.hrm.eureka.common.mapper.RoleMapper;
import com.hrm.eureka.common.mapper.RolePermissionMapper;
import com.hrm.eureka.common.model.User;
import com.hrm.eureka.common.principal.UserPrincipal;
import com.hrm.eureka.common.repository.RolePermissionRepository;
import com.hrm.eureka.common.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, RolePermissionRepository rolePermissionRepository) {
        this.userRepository = userRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[Common Service] Loading user by username: {}", username);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            log.error("[Common Service] User not found");
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        User user = optionalUser.get();
        // Authorities
        List<GrantedAuthority> authorityList = new ArrayList<>();
        List<PermissionDto> permissionDtoList = getPermissionsByRoleId(user.getRole().getRoleId());
        for (PermissionDto permissionDto : permissionDtoList) {
            authorityList.add(new SimpleGrantedAuthority(permissionDto.getPermissionName()));
        }
        log.info("[Common Service] Authorities: {}", authorityList.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        RoleDto roleDto = RoleMapper.toRoleDto(user.getRole());
        return new UserPrincipal(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                roleDto.getRoleName(),
                user.getDepartment().getDepartmentName(),
                authorityList
        );
    }

    public List<PermissionDto> getPermissionsByRoleId(Long roleId) {
        // Fetch permissions by role ID from the repository
        return rolePermissionRepository.findAllPermissionByRoleId(roleId).stream()
                .map(RolePermissionMapper::toPermissionDto)
                .collect(Collectors.toList());
    }
}

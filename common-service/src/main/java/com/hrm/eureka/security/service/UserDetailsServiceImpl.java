package com.hrm.eureka.security.service;

import com.hrm.eureka.security.dto.PermissionDto;
import com.hrm.eureka.security.dto.RoleDto;
import com.hrm.eureka.security.mapper.RoleMapper;
import com.hrm.eureka.security.mapper.RolePermissionMapper;
import com.hrm.eureka.security.model.User;
import com.hrm.eureka.security.principal.UserPrincipal;
import com.hrm.eureka.security.repository.RolePermissionRepository;
import com.hrm.eureka.security.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, RolePermissionRepository rolePermissionRepository) {
        this.userRepository = userRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

//        System.out.println(user.getUsername());
        List<GrantedAuthority> authorityList = new ArrayList<>();

        List<PermissionDto> permissionDtoList = getPermissionsByRoleId(user.getRole().getRoleId());
        for (PermissionDto permissionDto : permissionDtoList) {
            authorityList.add(new SimpleGrantedAuthority(permissionDto.getPermissionName()));
        }

        RoleDto roleDto = RoleMapper.toRoleDto(user.getRole());
        return new UserPrincipal(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getDepartmentId(),
                roleDto.getRoleName(),
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

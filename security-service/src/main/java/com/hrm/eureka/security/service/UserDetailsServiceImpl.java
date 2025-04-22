package com.hrm.eureka.security.service;

import com.hrm.eureka.permission.dto.RoleDto;
import com.hrm.eureka.security.client.PermissionClient;
import com.hrm.eureka.security.model.User;
import com.hrm.eureka.security.principal.UserPrincipal;
import com.hrm.eureka.security.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PermissionClient permissionClient;

    public UserDetailsServiceImpl(UserRepository userRepository, PermissionClient permissionClient) {
        this.userRepository = userRepository;
        this.permissionClient = permissionClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

//        System.out.println(user.getUsername());
        List<GrantedAuthority> authorityList = new ArrayList<>();
        if (permissionClient.getPermissionsByRoleId(user.getRoleId()).size() > 0) {
//            List<GrantedAuthority> authorityList = new ArrayList<>();
            permissionClient.getPermissionsByRoleId(user.getRoleId()).forEach(permission -> {
                authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName()));
            });

            RoleDto role = permissionClient.getRoleById(user.getRoleId());
            return new UserPrincipal(
                    user.getUserId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getDepartmentId(),
                    role.getRoleName(),
                    authorityList
            );
        } else {
            RoleDto role = permissionClient.getRoleById(user.getRoleId());
            return new UserPrincipal(
                    user.getUserId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getDepartmentId(),
                    role.getRoleName(),
                    authorityList
            );
        }
    }
}

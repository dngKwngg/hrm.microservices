package com.hrm.eureka.common.principal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private Long departmentId;
    private String roleName;
    private Collection<? extends GrantedAuthority> authorities;
}

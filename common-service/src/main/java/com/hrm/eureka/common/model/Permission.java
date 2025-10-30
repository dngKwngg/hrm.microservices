package com.hrm.eureka.common.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long permissionId;

    @Column(name="name", nullable = false, unique = true)
    private String permissionName;
}


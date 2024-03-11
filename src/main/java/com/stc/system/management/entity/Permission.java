package com.stc.system.management.entity;

import com.stc.system.management.enums.PermissionLevelEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permission")
public class Permission {

    @Id
    @SequenceGenerator(name = "permission_seq", sequenceName = "permission_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_seq")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum level;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_group_id")
    private PermissionGroup permissionGroup;
}

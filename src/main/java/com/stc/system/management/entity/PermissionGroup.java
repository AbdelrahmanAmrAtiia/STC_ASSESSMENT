package com.stc.system.management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "permission_group")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionGroup {

    @Id
    @SequenceGenerator(name = "permission_group_seq", sequenceName = "permission_group_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_group_seq")
    private Long id;

    @Column(name = "group_name", unique = true)
    private String groupName;

    @OneToMany()
    @JoinColumn(name = "permission_group_id")
    @JsonBackReference
    private List<Permission> permissions;

}

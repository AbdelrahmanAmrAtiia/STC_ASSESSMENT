package com.stc.system.management.entity;

import com.stc.system.management.enums.ItemTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "item")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @SequenceGenerator(name = "item_seq", sequenceName = "item_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ItemTypeEnum type;
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "permission_group_id")
    private PermissionGroup permissionGroup;

}

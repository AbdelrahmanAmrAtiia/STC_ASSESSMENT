package com.stc.system.management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "file")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class File {

    @Id
    @SequenceGenerator(name = "file_seq", sequenceName = "file_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
    private Long id;

    @Lob
    @Column(nullable = false)
    private byte[] fileBinary;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

}

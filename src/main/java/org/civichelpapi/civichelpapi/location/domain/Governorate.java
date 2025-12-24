package org.civichelpapi.civichelpapi.location.domain;

import jakarta.persistence.*;
import org.civichelpapi.civichelpapi.shared.domain.BaseEntity;

@Entity
public class Governorate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;
}
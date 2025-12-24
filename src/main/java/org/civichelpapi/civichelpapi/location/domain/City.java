package org.civichelpapi.civichelpapi.location.domain;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import org.civichelpapi.civichelpapi.shared.domain.BaseEntity;

@Entity
public class City extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Governorate governorate;
}
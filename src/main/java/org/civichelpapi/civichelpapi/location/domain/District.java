package org.civichelpapi.civichelpapi.location.domain;

import jakarta.persistence.*;
import org.civichelpapi.civichelpapi.shared.domain.BaseEntity;

@Entity
public class District extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private City city;
}
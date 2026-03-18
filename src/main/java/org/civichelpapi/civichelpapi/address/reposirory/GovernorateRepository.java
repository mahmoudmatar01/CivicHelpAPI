package org.civichelpapi.civichelpapi.address.reposirory;

import org.civichelpapi.civichelpapi.address.entity.Governorate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GovernorateRepository extends JpaRepository<Governorate, Integer> {
    Optional<Governorate> findByNameIgnoreCase(String name);
}

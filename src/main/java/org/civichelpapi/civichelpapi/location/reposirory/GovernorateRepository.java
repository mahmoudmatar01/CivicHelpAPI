package org.civichelpapi.civichelpapi.location.reposirory;

import org.civichelpapi.civichelpapi.location.domain.Governorate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GovernorateRepository extends JpaRepository<Governorate, Integer> {
}

package org.civichelpapi.civichelpapi.location.reposirory;

import org.civichelpapi.civichelpapi.location.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
}

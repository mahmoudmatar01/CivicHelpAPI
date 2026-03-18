package org.civichelpapi.civichelpapi.address.reposirory;

import org.civichelpapi.civichelpapi.address.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    List<District>findByCityId(Integer cityId);
}

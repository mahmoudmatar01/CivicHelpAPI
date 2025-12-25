package org.civichelpapi.civichelpapi.location.reposirory;

import org.civichelpapi.civichelpapi.location.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByGovernorateId(Integer governorateId);
}

package org.civichelpapi.civichelpapi.ngo.repository;

import org.civichelpapi.civichelpapi.ngo.entity.NgoOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NgoOfferRepository extends JpaRepository<NgoOffer, Integer> {

    boolean existsByNgoIdAndReportId(Long ngoId, Long reportId);
    List<NgoOffer> findByReportId(Long reportId);

}

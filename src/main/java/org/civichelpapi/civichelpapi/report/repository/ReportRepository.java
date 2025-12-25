package org.civichelpapi.civichelpapi.report.repository;

import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByCitizenId(Long citizenId);
    @Query("""
        SELECT r FROM Report r
        WHERE r.status IN (
                 org.civichelpapi.civichelpapi.report.enums.Status.OPEN,
                 org.civichelpapi.civichelpapi.report.enums.Status.ASSIGNED,
                 org.civichelpapi.civichelpapi.report.enums.Status.IN_PROGRESS
        )
    """)
    List<Report> findByStatusIn();
    Page<Report> findByAssignedAuthorityId(
            Long authorityId,
            Pageable pageable
    );
    Page<Report> findByDistrictCityIdAndStatusIn(
            Integer cityId,
            List<Status> statuses,
            Pageable pageable
    );

    @Query("""
    SELECT r FROM Report r
    WHERE r.status IN :statuses
      AND r.slaBreached = false
      AND r.slaDeadline < :now
""")
    List<Report> findReportsBreachingSla(
            @Param("statuses") List<Status> statuses,
            @Param("now") LocalDateTime now
    );
}

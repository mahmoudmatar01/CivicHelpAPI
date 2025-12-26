package org.civichelpapi.civichelpapi.report.repository;

import org.civichelpapi.civichelpapi.dashboard.dto.StatusChartDto;
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

    long countByStatus(Status status);

    @Query("""
        SELECT COUNT(r)
        FROM Report r
        WHERE r.slaDeadline < :now
          AND r.status NOT IN (
          org.civichelpapi.civichelpapi.report.enums.Status.RESOLVED,
          org.civichelpapi.civichelpapi.report.enums.Status.REJECTED
          )
    """)
    long countOverdueReports(LocalDateTime now);

    @Query("""
         SELECT new org.civichelpapi.civichelpapi.dashboard.dto.StatusChartDto(
            r.status,
            count(r)
         )
            FROM Report r
            GROUP BY r.status
    """)
    List<StatusChartDto> countReportsByStatus();

    long countByStatusIn(List<Status> statuses);

    @Query("""
    SELECT COUNT(r)
    FROM Report r
    WHERE r.status = org.civichelpapi.civichelpapi.report.enums.Status.RESOLVED
      AND r.resolvedAt <= r.slaDeadline
    """)
    long countResolvedWithinSla();

    @Query("""
    SELECT COUNT(r)
    FROM Report r
    WHERE r.status = 'RESOLVED'
      AND r.resolvedAt > r.slaDeadline
    """)
    long countResolvedLate();

    @Query("""
    SELECT COUNT(r)
    FROM Report r
    WHERE r.status IN (
          org.civichelpapi.civichelpapi.report.enums.Status.OPEN,
          org.civichelpapi.civichelpapi.report.enums.Status.ASSIGNED,
          org.civichelpapi.civichelpapi.report.enums.Status.IN_PROGRESS
    )
      AND r.slaDeadline < CURRENT_TIMESTAMP
    """)
    long countUnresolvedOverdue();

}

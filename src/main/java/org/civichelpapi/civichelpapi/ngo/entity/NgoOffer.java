package org.civichelpapi.civichelpapi.ngo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.shared.entity.BaseEntity;
import org.civichelpapi.civichelpapi.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "ngo_offers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"ngo_id", "report_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NgoOffer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ngo_id")
    private User ngo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "report_id")
    private Report report;

    @Column(nullable = false)
    private LocalDateTime offeredAt;
}

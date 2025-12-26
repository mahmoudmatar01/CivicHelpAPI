package org.civichelpapi.civichelpapi.report.entity;

import jakarta.persistence.*;
import lombok.*;
import org.civichelpapi.civichelpapi.category.entity.Category;
import org.civichelpapi.civichelpapi.category.enums.Priority;
import org.civichelpapi.civichelpapi.location.entity.District;
import org.civichelpapi.civichelpapi.report.enums.Status;
import org.civichelpapi.civichelpapi.shared.entity.BaseEntity;
import org.civichelpapi.civichelpapi.user.entity.User;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "reports")
@Getter
@Setter
@RequiredArgsConstructor
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private User citizen;

    @ManyToOne(optional = false)
    private Category category;

    @ManyToOne(optional = false)
    private District district;

    @Column(nullable = false, length = 1000)
    private String description;

//    @ElementCollection
//    private List<String> imageUrls;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    private User assignedAuthority;

    @Column(length = 1000)
    private String resolutionNote;

    @Column(length = 1000)
    private String rejectionReason;

    private LocalDateTime resolvedAt;

    private boolean slaBreached = false;

    private LocalDateTime slaDeadline;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Report report = (Report) o;
        return getId() != null && Objects.equals(getId(), report.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public boolean isUnresolved() {
        return this.status == Status.OPEN
                || this.status == Status.ASSIGNED
                || this.status == Status.IN_PROGRESS;
    }

}


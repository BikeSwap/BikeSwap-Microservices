package pe.edu.bikeswap.bike_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="availabilities")
public class AvailabilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="availability_type", nullable = false)
    private boolean availabilityType;

    @Column(name="availability_start_date", nullable = false)
    private LocalDate availabilityStartDate;

    @Column(name="availability_end_date", nullable = false)
    private LocalDate availabilityEndDate;

    @ManyToOne
    @JoinColumn(name = "bikes_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_BICYCLE_ID"))
    private BikeEntity bicycle;
}

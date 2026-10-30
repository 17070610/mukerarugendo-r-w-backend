package rw.mukerarugendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.mukerarugendo.domain.Destination;
import rw.mukerarugendo.domain.TourGuide;

import java.util.UUID;

public interface TourGuideRepository extends JpaRepository<TourGuide, UUID> {
    boolean existsByDestination(Destination destination);
}

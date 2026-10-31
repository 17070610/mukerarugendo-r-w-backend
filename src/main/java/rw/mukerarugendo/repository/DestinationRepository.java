package rw.mukerarugendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.mukerarugendo.domain.Destination;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, UUID> {
    boolean existsBySiteNumber(int siteNumber);
    Optional<Destination> findBySiteNumber(int siteNumber);

    @Query("SELECT d FROM Destination d WHERE NOT EXISTS " +
            "(SELECT g FROM TourGuide g WHERE g.destination = d) ORDER BY d.siteNumber")
    List<Destination> findFreeDestinations();
}

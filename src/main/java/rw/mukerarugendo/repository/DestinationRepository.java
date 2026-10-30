package rw.mukerarugendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.mukerarugendo.domain.Destination;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, UUID> {
    boolean existsBySiteNumber(int siteNumber);
    Optional<Destination> findBySiteNumber(int siteNumber);
}

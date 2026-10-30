package rw.mukerarugendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.mukerarugendo.domain.Traveller;

import java.util.UUID;

@Repository
public interface TravellerRepository extends JpaRepository<Traveller, UUID> {
    boolean existsByPassportNumber(String passportNumber);
}

package rw.mukerarugendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.mukerarugendo.domain.TourBooking;
import rw.mukerarugendo.domain.TourGuide;
import rw.mukerarugendo.domain.Traveller;

import java.util.UUID;

public interface TourBookingRepository extends JpaRepository<TourBooking, UUID> {
    boolean existsByTourGuide(TourGuide tourGuide);
    boolean existsByTraveller(Traveller traveller);
}

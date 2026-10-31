package rw.mukerarugendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.mukerarugendo.domain.TourBooking;
import rw.mukerarugendo.domain.TourGuide;
import rw.mukerarugendo.domain.TourStatus;
import rw.mukerarugendo.domain.Traveller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TourBookingRepository extends JpaRepository<TourBooking, UUID> {
    boolean existsByTourGuide(TourGuide tourGuide);
    boolean existsByTraveller(Traveller traveller);
    List<TourBooking> findByStatusOrderByTourDateAsc(TourStatus status);
    List<TourBooking> findByTourDateBetweenOrderByTourDateAsc(LocalDate start, LocalDate end);
}

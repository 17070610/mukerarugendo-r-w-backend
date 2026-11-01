package rw.mukerarugendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    boolean existsByTourGuideIdAndTourDateAndStatusNot(UUID tourGuideId, LocalDate tourDate, TourStatus status);

    @Query("SELECT b.status, COUNT(b) FROM TourBooking b GROUP BY b.status")
    List<Object[]> countTourBookingsByStatus();

    @Modifying
    @Query("UPDATE TourBooking b SET b.status = rw.mukerarugendo.domain.TourStatus.CANCELLED " +
            "WHERE b.tourGuide.id = :tourGuideId AND b.tourDate = :date " +
            "AND b.status <> rw.mukerarugendo.domain.TourStatus.VISITED")
    int cancelDay(@Param("tourGuideId") UUID tourGuideId, @Param("date") LocalDate date);
}

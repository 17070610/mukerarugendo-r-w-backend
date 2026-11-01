package rw.mukerarugendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.mukerarugendo.domain.TourBooking;
import rw.mukerarugendo.domain.TourFeedback;

import java.util.List;
import java.util.UUID;

@Repository
public interface TourFeedbackRepository extends JpaRepository<TourFeedback, UUID> {
    boolean existsByTourBooking(TourBooking tourBooking);

    @Query("SELECT d FROM TourFeedback d WHERE d.tourBooking.traveller.passportNumber = :passportNumber " +
            "ORDER BY d.submittedDate DESC")
    List<TourFeedback> findTravellerHistory(@Param("passportNumber") String passportNumber);
}

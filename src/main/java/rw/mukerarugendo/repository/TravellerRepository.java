package rw.mukerarugendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.mukerarugendo.domain.Traveller;

import java.util.List;
import java.util.UUID;

@Repository
public interface TravellerRepository extends JpaRepository<Traveller, UUID> {
    boolean existsByPassportNumber(String passportNumber);
    List<Traveller> findByNationalityIgnoreCaseOrderByLastNameAsc(String nationality);

    @Query("SELECT DISTINCT b.traveller FROM TourBooking b WHERE b.tourGuide.id = :tourGuideId")
    List<Traveller> findTravellersOfTourGuide(@Param("tourGuideId") UUID tourGuideId);
}

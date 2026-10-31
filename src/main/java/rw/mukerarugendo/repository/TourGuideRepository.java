package rw.mukerarugendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.mukerarugendo.domain.Destination;
import rw.mukerarugendo.domain.TourGuide;

import java.util.List;
import java.util.UUID;

public interface TourGuideRepository extends JpaRepository<TourGuide, UUID> {
    boolean existsByDestination(Destination destination);

    @Query("SELECT DISTINCT g FROM TourGuide g JOIN g.tourTypes t " +
            "WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<TourGuide> findGuidesByTourTypeName(@Param("name") String name);
}

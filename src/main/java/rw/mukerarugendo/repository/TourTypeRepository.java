package rw.mukerarugendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.mukerarugendo.domain.TourType;

import java.util.List;
import java.util.UUID;

@Repository
public interface TourTypeRepository extends JpaRepository<TourType, UUID> {
    boolean existsByNameIgnoreCase(String name);

    @Query("SELECT t FROM TourType t WHERE t.guides IS EMPTY")
    List<TourType> findUnusedTourTypes();
}

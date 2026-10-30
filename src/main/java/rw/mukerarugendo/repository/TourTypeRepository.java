package rw.mukerarugendo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.mukerarugendo.domain.TourType;

import java.util.UUID;

@Repository
public interface TourTypeRepository extends JpaRepository<TourType, UUID> {
    boolean existsByNameIgnoreCase(String name);
}

package rw.mukerarugendo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.mukerarugendo.domain.TourGuide;
import rw.mukerarugendo.domain.TourType;
import rw.mukerarugendo.repository.TourGuideRepository;
import rw.mukerarugendo.repository.TourTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TourTypeService {
    @Autowired
    private TourTypeRepository tourTypeRepo;
    @Autowired
    private TourGuideRepository tourGuideRepo;

    public String saveTourType(TourType tourType) {
        if (tourTypeRepo.existsByNameIgnoreCase(tourType.getName()))
            return "That tour type already exists";
        tourTypeRepo.save(tourType);
        return "Tour type saved successfully";
    }

    public List<TourType> getTourTypes() {
        return tourTypeRepo.findAll();
    }

    public Optional<TourType> getTourTypeById(UUID id) {
        return tourTypeRepo.findById(id);
    }

    public Optional<TourType> updateTourType(UUID id, TourType newTourType) {
        Optional<TourType> foundTourType = tourTypeRepo.findById(id);
        if (foundTourType.isEmpty())
            return Optional.empty();
        TourType existingTourType = foundTourType.get();
        existingTourType.setName(newTourType.getName());
        TourType savedTourType = tourTypeRepo.save(existingTourType);
        return Optional.of(savedTourType);
    }

    public String deleteTourType(UUID id) {
        Optional<TourType> foundTourType = tourTypeRepo.findById(id);
        if (foundTourType.isEmpty())
            return "The tour type with that id does not exist";
        TourType tourType = foundTourType.get();
        for (TourGuide tourGuide : tourType.getGuides()) {
            tourGuide.getTourTypes().remove(tourType);
            tourGuideRepo.save(tourGuide);
        }
        tourTypeRepo.delete(tourType);
        return "Tour type deleted successfully";
    }
}

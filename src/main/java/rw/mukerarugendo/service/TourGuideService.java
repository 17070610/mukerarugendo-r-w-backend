package rw.mukerarugendo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.mukerarugendo.domain.Destination;
import rw.mukerarugendo.domain.TourGuide;
import rw.mukerarugendo.domain.TourType;
import rw.mukerarugendo.repository.DestinationRepository;
import rw.mukerarugendo.repository.TourBookingRepository;
import rw.mukerarugendo.repository.TourGuideRepository;
import rw.mukerarugendo.repository.TourTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TourGuideService {

    @Autowired
    private TourGuideRepository tourGuideRepo;
    @Autowired
    private DestinationRepository destinationRepo;
    @Autowired
    private TourTypeRepository tourTypeRepo;
    @Autowired
    private TourBookingRepository tourBookingRepo;

    public String saveTourGuide(TourGuide tourGuide, Integer siteNumber) {
        if (siteNumber == null) {
            tourGuideRepo.save(tourGuide);
            return "Tour guide saved successfully";
        }
        Optional<Destination> foundDestination = destinationRepo.findBySiteNumber(siteNumber);
        if (foundDestination.isEmpty())
            return "The destination with that siteNumber does not exist";
        if (tourGuideRepo.existsByDestination(foundDestination.get()))
            return "That destination is taken by another tour guide";
        tourGuide.setDestination(foundDestination.get());
        tourGuideRepo.save(tourGuide);
        return "Tour guide saved successfully";
    }

    public List<TourGuide> getTourGuides() {
        return tourGuideRepo.findAll();
    }

    public Optional<TourGuide> getTourGuideById(UUID id) {
        return tourGuideRepo.findById(id);
    }

    public Optional<TourGuide> updateTourGuide(UUID id, TourGuide newTourGuide) {
        Optional<TourGuide> foundTourGuide = tourGuideRepo.findById(id);
        if (foundTourGuide.isEmpty())
            return Optional.empty();
        TourGuide existingTourGuide = foundTourGuide.get();
        existingTourGuide.setFirstName(newTourGuide.getFirstName());
        existingTourGuide.setLastName(newTourGuide.getLastName());
        existingTourGuide.setPhone(newTourGuide.getPhone());
        existingTourGuide.setLanguages(newTourGuide.getLanguages());
        TourGuide savedTourGuide = tourGuideRepo.save(existingTourGuide);
        return Optional.of(savedTourGuide);
    }

    public String deleteTourGuide(UUID id) {
        Optional<TourGuide> foundTourGuide = tourGuideRepo.findById(id);
        if (foundTourGuide.isEmpty())
            return "The tour guide with that id does not exist";
        if (tourBookingRepo.existsByTourGuide(foundTourGuide.get()))
            return "That tour guide has tour bookings, delete them first";
        tourGuideRepo.deleteById(id);
        return "Tour guide deleted successfully";
    }

    public String addTourType(UUID tourGuideId, UUID tourTypeId) {
        Optional<TourGuide> foundTourGuide = tourGuideRepo.findById(tourGuideId);
        if (foundTourGuide.isEmpty())
            return "The tour guide with that id does not exist";
        Optional<TourType> foundTourType = tourTypeRepo.findById(tourTypeId);
        if (foundTourType.isEmpty())
            return "That tour type does not exist";
        TourGuide tourGuide = foundTourGuide.get();
        TourType tourType = foundTourType.get();
        if (tourGuide.getTourTypes().contains(tourType))
            return "The tour guide already has that tour type";
        tourGuide.getTourTypes().add(tourType);
        tourGuideRepo.save(tourGuide);
        return "Tour type added to the tour guide";
    }
}
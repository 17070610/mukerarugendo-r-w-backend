package rw.mukerarugendo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.mukerarugendo.domain.TourBooking;
import rw.mukerarugendo.domain.TourGuide;
import rw.mukerarugendo.domain.Traveller;
import rw.mukerarugendo.repository.TourBookingRepository;
import rw.mukerarugendo.repository.TourGuideRepository;
import rw.mukerarugendo.repository.TravellerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TourBookingService {

    @Autowired
    private TourBookingRepository tourBookingRepo;
    @Autowired
    private TravellerRepository travellerRepo;
    @Autowired
    private TourGuideRepository tourGuideRepo;

    public String saveTourBooking(TourBooking tourBooking, UUID travellerId, UUID tourGuideId) {
        Optional<Traveller> foundTraveller = travellerRepo.findById(travellerId);
        if (foundTraveller.isEmpty())
            return "The traveller with that id does not exist";
        Optional<TourGuide> foundTourGuide = tourGuideRepo.findById(tourGuideId);
        if (foundTourGuide.isEmpty())
            return "The tour guide with that id does not exist";
        tourBooking.setTraveller(foundTraveller.get());
        tourBooking.setTourGuide(foundTourGuide.get());
        tourBookingRepo.save(tourBooking);
        return "Tour booking saved successfully";
    }

    public List<TourBooking> getTourBookings() {
        return tourBookingRepo.findAll();
    }

    public Optional<TourBooking> getTourBookingById(UUID id) {
        return tourBookingRepo.findById(id);
    }

    public Optional<TourBooking> updateTourBooking(UUID id, TourBooking newTourBooking) {
        Optional<TourBooking> foundTourBooking = tourBookingRepo.findById(id);
        if (foundTourBooking.isEmpty())
            return Optional.empty();
        TourBooking existingTourBooking = foundTourBooking.get();
        existingTourBooking.setTourDate(newTourBooking.getTourDate());
        existingTourBooking.setGroupSize(newTourBooking.getGroupSize());
        existingTourBooking.setStatus(newTourBooking.getStatus());
        TourBooking savedTourBooking = tourBookingRepo.save(existingTourBooking);
        return Optional.of(savedTourBooking);
    }

    public String deleteTourBooking(UUID id) {
        Optional<TourBooking> foundTourBooking = tourBookingRepo.findById(id);
        if (foundTourBooking.isEmpty())
            return "The tour booking with that id does not exist";
        tourBookingRepo.deleteById(id);
        return "Tour booking deleted successfully";
    }
}

package rw.mukerarugendo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.mukerarugendo.domain.TourBooking;
import rw.mukerarugendo.domain.TourGuide;
import rw.mukerarugendo.domain.TourStatus;
import rw.mukerarugendo.domain.Traveller;
import rw.mukerarugendo.repository.TourBookingRepository;
import rw.mukerarugendo.repository.TourGuideRepository;
import rw.mukerarugendo.repository.TravellerRepository;

import java.time.LocalDate;
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
        if (tourBooking.getTourDate().isBefore(LocalDate.now()))
            return "Tour booking date cannot be in the past";
        boolean tourGuideBooked = tourBookingRepo.existsByTourGuideIdAndTourDateAndStatusNot(
                tourGuideId,
                tourBooking.getTourDate(),
                TourStatus.CANCELLED
        );
        if (tourGuideBooked)
            return "Tour guide is already booked on that date";
        tourBooking.setStatus(TourStatus.RESERVED);
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

    public String updateStatus(UUID id, TourStatus newStatus) {
        Optional<TourBooking> foundTourBooking = tourBookingRepo.findById(id);
        if (foundTourBooking.isEmpty())
            return "The tour booking with that id does not exist";
        TourBooking tourBooking = foundTourBooking.get();
        TourStatus currentStatus = tourBooking.getStatus();
        boolean allowed = false;
        if (currentStatus == TourStatus.RESERVED
                && (newStatus == TourStatus.PAID || newStatus == TourStatus.CANCELLED))
            allowed = true;
        if (currentStatus == TourStatus.PAID
                && (newStatus == TourStatus.VISITED || newStatus == TourStatus.CANCELLED))
            allowed = true;
        if (!allowed)
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        tourBooking.setStatus(newStatus);
        tourBookingRepo.save(tourBooking);
        return "Tour booking status updated successfully";
    }

    public List<TourBooking> getTourBookingsByStatus(TourStatus status) {
        return tourBookingRepo.findByStatusOrderByTourDateAsc(status);
    }

    public List<TourBooking> getTourBookingsBetween(LocalDate start, LocalDate end) {
        return tourBookingRepo.findByTourDateBetweenOrderByTourDateAsc(start, end);
    }
}

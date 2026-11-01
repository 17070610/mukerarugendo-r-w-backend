package rw.mukerarugendo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.mukerarugendo.domain.TourBooking;
import rw.mukerarugendo.domain.TourFeedback;
import rw.mukerarugendo.domain.TourStatus;
import rw.mukerarugendo.repository.TourBookingRepository;
import rw.mukerarugendo.repository.TourFeedbackRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TourFeedbackService {

    @Autowired
    private TourFeedbackRepository tourFeedbackRepo;
    @Autowired
    private TourBookingRepository tourBookingRepo;

    public String saveTourFeedback(TourFeedback tourFeedback, UUID tourBookingId) {
        Optional<TourBooking> foundTourBooking = tourBookingRepo.findById(tourBookingId);
        if (foundTourBooking.isEmpty())
            return "The tour booking with that id does not exist";
        if (foundTourBooking.get().getStatus() != TourStatus.VISITED)
            return "Tour feedbacks are only for VISITED tour bookings";
        tourFeedback.setSubmittedDate(LocalDate.now());
        tourFeedback.setTourBooking(foundTourBooking.get());
        tourFeedbackRepo.save(tourFeedback);
        return "Tour feedback saved successfully";
    }

    public List<TourFeedback> getTourFeedbacks() {
        return tourFeedbackRepo.findAll();
    }

    public Optional<TourFeedback> getTourFeedbackById(UUID id) {
        return tourFeedbackRepo.findById(id);
    }

    public List<TourFeedback> getTravellerHistory(String passportNumber) {
        return tourFeedbackRepo.findTravellerHistory(passportNumber);
    }

    public String deleteTourFeedback(UUID id) {
        if (!tourFeedbackRepo.existsById(id))
            return "The tour feedback with that id does not exist";
        tourFeedbackRepo.deleteById(id);
        return "Tour feedback deleted successfully";
    }
}

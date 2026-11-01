package rw.mukerarugendo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.mukerarugendo.domain.TourStatus;
import rw.mukerarugendo.repository.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private TravellerRepository travellerRepo;
    @Autowired
    private TourGuideRepository tourGuideRepo;
    @Autowired
    private DestinationRepository destinationRepo;
    @Autowired
    private TourBookingRepository tourBookingRepo;
    @Autowired
    private TourFeedbackRepository tourFeedbackRepo;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("travellers", travellerRepo.count());
        summary.put("tourGuides", tourGuideRepo.count());
        summary.put("freeDestinations", (long) destinationRepo.findFreeDestinations().size());
        summary.put("toursToday", tourBookingRepo.countByTourDate(LocalDate.now()));
        summary.put("reserved", tourBookingRepo.countByStatus(TourStatus.RESERVED));
        summary.put("feedbacks", tourFeedbackRepo.count());
        return summary;
    }
}

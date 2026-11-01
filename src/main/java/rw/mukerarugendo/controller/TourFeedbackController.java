package rw.mukerarugendo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.mukerarugendo.domain.TourFeedback;
import rw.mukerarugendo.service.TourFeedbackService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/tour-feedbacks")
public class TourFeedbackController {

    @Autowired
    private TourFeedbackService tourFeedbackServe;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveTourFeedback(@RequestBody TourFeedback tourFeedback,
                                              @RequestParam UUID tourBookingId) {
        String returnedMessage = tourFeedbackServe.saveTourFeedback(tourFeedback, tourBookingId);
        if (returnedMessage.equals("Tour feedback saved successfully"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        else if (returnedMessage.equals("The tour booking with that id does not exist"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<TourFeedback>> getTourFeedbacks() {
        return ResponseEntity.ok(tourFeedbackServe.getTourFeedbacks());
    }

    @GetMapping(value = "/traveller/{passportNumber}")
    public ResponseEntity<List<TourFeedback>> getTravellerHistory(@PathVariable String passportNumber) {
        return ResponseEntity.ok(tourFeedbackServe.getTravellerHistory(passportNumber));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTourFeedbackById(@PathVariable UUID id) {
        Optional<TourFeedback> foundTourFeedback = tourFeedbackServe.getTourFeedbackById(id);
        if (foundTourFeedback.isPresent())
            return ResponseEntity.ok(foundTourFeedback.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The tour feedback with that id does not exist");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteTourFeedback(@PathVariable UUID id) {
        String returnedMessage = tourFeedbackServe.deleteTourFeedback(id);
        if (returnedMessage.equals("Tour feedback deleted successfully"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }
}

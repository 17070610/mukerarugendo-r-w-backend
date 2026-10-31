package rw.mukerarugendo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.mukerarugendo.domain.TourBooking;
import rw.mukerarugendo.domain.TourStatus;
import rw.mukerarugendo.service.TourBookingService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/tour-bookings")
public class TourBookingController {

    @Autowired
    private TourBookingService tourBookingServe;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveTourBooking(@RequestBody TourBooking tourBooking,
                                             @RequestParam UUID travellerId,
                                             @RequestParam UUID tourGuideId) {
        String returnedMessage = tourBookingServe.saveTourBooking(tourBooking, travellerId, tourGuideId);
        if (returnedMessage.equals("Tour booking saved successfully"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        else if (returnedMessage.equals("Tour booking date cannot be in the past"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
        else if (returnedMessage.equals("Tour guide is already booked on that date"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<TourBooking>> getTourBookings() {
        return ResponseEntity.ok(tourBookingServe.getTourBookings());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTourBookingById(@PathVariable UUID id) {
        Optional<TourBooking> foundTourBooking = tourBookingServe.getTourBookingById(id);
        if (foundTourBooking.isPresent())
            return ResponseEntity.ok(foundTourBooking.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The tour booking with that id does not exist");
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateTourBooking(@PathVariable UUID id, @RequestBody TourBooking tourBooking) {
        Optional<TourBooking> updatedTourBooking = tourBookingServe.updateTourBooking(id, tourBooking);
        if (updatedTourBooking.isPresent())
            return ResponseEntity.ok(updatedTourBooking.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The tour booking with that id does not exist");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteTourBooking(@PathVariable UUID id) {
        String returnedMessage = tourBookingServe.deleteTourBooking(id);
        if (returnedMessage.equals("Tour booking deleted successfully"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/by-status")
    public ResponseEntity<List<TourBooking>> getTourBookingsByStatus(@RequestParam TourStatus status) {
        return ResponseEntity.ok(tourBookingServe.getTourBookingsByStatus(status));
    }

    @GetMapping(value = "/between")
    public ResponseEntity<List<TourBooking>> getTourBookingsBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(tourBookingServe.getTourBookingsBetween(startDate, endDate));
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestParam TourStatus status) {
        String returnedMessage = tourBookingServe.updateStatus(id, status);
        if (returnedMessage.equals("Tour booking status updated successfully"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        else if (returnedMessage.equals("The tour booking with that id does not exist"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
    }
}

package rw.mukerarugendo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.mukerarugendo.domain.TourGuide;
import rw.mukerarugendo.service.TourGuideService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/tour-guides")
public class TourGuideController {

    @Autowired
    private TourGuideService tourGuideServe;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveTourGuide(@RequestBody TourGuide tourGuide,
                                           @RequestParam(required = false) Integer siteNumber) {
        String returnedMessage = tourGuideServe.saveTourGuide(tourGuide, siteNumber);
        if (returnedMessage.equals("Tour guide saved successfully"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        else if (returnedMessage.equals("The destination with that siteNumber does not exist"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<TourGuide>> getTourGuides() {
        return ResponseEntity.ok(tourGuideServe.getTourGuides());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTourGuideById(@PathVariable UUID id) {
        Optional<TourGuide> foundTourGuide = tourGuideServe.getTourGuideById(id);
        if (foundTourGuide.isPresent())
            return ResponseEntity.ok(foundTourGuide.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The tour guide with that id does not exist");
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateTourGuide(@PathVariable UUID id, @RequestBody TourGuide tourGuide) {
        Optional<TourGuide> updatedTourGuide = tourGuideServe.updateTourGuide(id, tourGuide);
        if (updatedTourGuide.isPresent())
            return ResponseEntity.ok(updatedTourGuide.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The tour guide with that id does not exist");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteTourGuide(@PathVariable UUID id) {
        String returnedMessage = tourGuideServe.deleteTourGuide(id);
        if (returnedMessage.equals("Tour guide deleted successfully"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        else if (returnedMessage.equals("The tour guide with that id does not exist"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
    }

    @PutMapping(value = "/{tourGuideId}/tour-types/{tourTypeId}")
    public ResponseEntity<?> addTourType(@PathVariable UUID tourGuideId, @PathVariable UUID tourTypeId) {
        String returnedMessage = tourGuideServe.addTourType(tourGuideId, tourTypeId);
        if (returnedMessage.equals("Tour type added to the tour guide"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        else if (returnedMessage.equals("The tour guide with that id does not exist")
                || returnedMessage.equals("That tour type does not exist"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
    }
}
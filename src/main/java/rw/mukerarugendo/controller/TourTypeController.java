package rw.mukerarugendo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.mukerarugendo.domain.TourType;
import rw.mukerarugendo.service.TourTypeService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/tour-types")
public class TourTypeController {

    @Autowired
    private TourTypeService tourTypeServe;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveTourType(@RequestBody TourType tourType) {
        String returnedMessage = tourTypeServe.saveTourType(tourType);
        if (returnedMessage.equals("Tour type saved successfully"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<TourType>> getTourTypes() {
        return ResponseEntity.ok(tourTypeServe.getTourTypes());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTourTypeById(@PathVariable UUID id) {
        Optional<TourType> foundTourType = tourTypeServe.getTourTypeById(id);
        if (foundTourType.isPresent())
            return ResponseEntity.ok(foundTourType.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The tour type with the given id does not exist");
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateTourType(@PathVariable UUID id, @RequestBody TourType tourType) {
        Optional<TourType> updatedTourType = tourTypeServe.updateTourType(id, tourType);
        if (updatedTourType.isPresent())
            return ResponseEntity.ok(updatedTourType.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The tour type with that id does not exist");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteTourType(@PathVariable UUID id) {
        String returnedMessage = tourTypeServe.deleteTourType(id);
        if (returnedMessage.equals("Tour type deleted successfully"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/unused")
    public ResponseEntity<List<TourType>> getUnusedTourTypes() {
        return ResponseEntity.ok(tourTypeServe.getUnusedTourTypes());
    }
}

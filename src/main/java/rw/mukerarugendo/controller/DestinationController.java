package rw.mukerarugendo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.mukerarugendo.domain.Destination;
import rw.mukerarugendo.service.DestinationService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationServe;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveDestination(@RequestBody Destination destination) {
        String returnedMessage = destinationServe.saveDestination(destination);
        if (returnedMessage.equals("Destination saved successfully"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Destination>> getDestinations() {
        return ResponseEntity.ok(destinationServe.getDestinations());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDestinationById(@PathVariable UUID id) {
        Optional<Destination> foundDestination = destinationServe.getDestinationById(id);
        if (foundDestination.isPresent())
            return ResponseEntity.ok(foundDestination.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The destination with that id does not exist");
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateDestination(@PathVariable UUID id, @RequestBody Destination destination) {
        Optional<Destination> updatedDestination = destinationServe.updateDestination(id, destination);
        if (updatedDestination.isPresent())
            return ResponseEntity.ok(updatedDestination.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The destination with that id does not exist");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteDestination(@PathVariable UUID id) {
        String returnedMessage = destinationServe.deleteDestination(id);
        if (returnedMessage.equals("Destination deleted successfully"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        else if (returnedMessage.equals("The destination with that id does not exist"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/free")
    public ResponseEntity<List<Destination>> getFreeDestinations() {
        return ResponseEntity.ok(destinationServe.getFreeDestinations());
    }
}

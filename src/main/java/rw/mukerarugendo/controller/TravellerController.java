package rw.mukerarugendo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.mukerarugendo.domain.Traveller;
import rw.mukerarugendo.service.TravellerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/travellers")
public class TravellerController {

    @Autowired
    private TravellerService travellerServe;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveTraveller(@RequestBody Traveller traveller) {
        String returnedMessage = travellerServe.saveTraveller(traveller);
        if (returnedMessage.equals("Traveller saved successfully"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Traveller>> getTravellers() {
        return ResponseEntity.ok(travellerServe.getTravellers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTravellerById(@PathVariable UUID id) {
        Optional<Traveller> foundTraveller = travellerServe.getTravellerById(id);
        if (foundTraveller.isPresent())
            return ResponseEntity.ok(foundTraveller.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The traveller with that id does not exist");
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateTraveller(@PathVariable UUID id, @RequestBody Traveller traveller) {
        Optional<Traveller> updatedTraveller = travellerServe.updateTraveller(id, traveller);
        if (updatedTraveller.isPresent())
            return ResponseEntity.ok(updatedTraveller.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The traveller with that id does not exist");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteTraveller(@PathVariable UUID id) {
        String returnedMessage = travellerServe.deleteTraveller(id);
        if (returnedMessage.equals("Traveller deleted successfully"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        else if (returnedMessage.equals("The traveller with that id does not exist"))
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/by-nationality")
    public ResponseEntity<List<Traveller>> getTravellersByNationality(@RequestParam String nationality) {
        return ResponseEntity.ok(travellerServe.getTravellersByNationality(nationality));
    }
}

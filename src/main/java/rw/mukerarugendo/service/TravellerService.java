package rw.mukerarugendo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.mukerarugendo.domain.Traveller;
import rw.mukerarugendo.repository.TourBookingRepository;
import rw.mukerarugendo.repository.TourGuideRepository;
import rw.mukerarugendo.repository.TravellerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TravellerService {

    @Autowired
    private TravellerRepository travellerRepo;
    @Autowired
    private TourBookingRepository tourBookingRepo;
    @Autowired
    private TourGuideRepository tourGuideRepo;

    public String saveTraveller(Traveller traveller) {
        if (travellerRepo.existsByPassportNumber(traveller.getPassportNumber()))
            return "A traveller with that passportNumber already exists";
        travellerRepo.save(traveller);
        return "Traveller saved successfully";
    }

    public List<Traveller> getTravellers() {
        return travellerRepo.findAll();
    }

    public Optional<Traveller> getTravellerById(UUID id) {
        return travellerRepo.findById(id);
    }

    public Optional<Traveller> updateTraveller(UUID id, Traveller newTraveller) {
        Optional<Traveller> foundTraveller = travellerRepo.findById(id);
        if (foundTraveller.isEmpty())
            return Optional.empty();
        Traveller existingTraveller = foundTraveller.get();
        existingTraveller.setFirstName(newTraveller.getFirstName());
        existingTraveller.setLastName(newTraveller.getLastName());
        existingTraveller.setPassportNumber(newTraveller.getPassportNumber());
        existingTraveller.setEmail(newTraveller.getEmail());
        existingTraveller.setNationality(newTraveller.getNationality());
        Traveller savedTraveller = travellerRepo.save(existingTraveller);
        return Optional.of(savedTraveller);
    }

    public String deleteTraveller(UUID id) {
        Optional<Traveller> foundTraveller = travellerRepo.findById(id);
        if (foundTraveller.isEmpty())
            return "The traveller with that id does not exist";
        if (tourBookingRepo.existsByTraveller(foundTraveller.get()))
            return "That traveller has tour bookings, delete them first";
        travellerRepo.deleteById(id);
        return "Traveller deleted successfully";
    }

    public List<Traveller> getTravellersByNationality(String nationality) {
        return travellerRepo.findByNationalityIgnoreCaseOrderByLastNameAsc(nationality);
    }

    public Optional<List<Traveller>> getTravellersOfTourGuide(UUID tourGuideId) {
        if (!tourGuideRepo.existsById(tourGuideId))
            return Optional.empty();
        return Optional.of(travellerRepo.findTravellersOfTourGuide(tourGuideId));
    }
}
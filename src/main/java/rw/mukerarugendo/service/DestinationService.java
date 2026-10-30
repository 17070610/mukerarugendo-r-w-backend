package rw.mukerarugendo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.mukerarugendo.domain.Destination;
import rw.mukerarugendo.repository.DestinationRepository;
import rw.mukerarugendo.repository.TourGuideRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepo;
    @Autowired
    private TourGuideRepository tourGuideRepo;

    public String saveDestination(Destination destination) {
        if (destinationRepo.existsBySiteNumber(destination.getSiteNumber()))
            return "A destination with that siteNumber already exists";
        destinationRepo.save(destination);
        return "Destination saved successfully";
    }

    public List<Destination> getDestinations() {
        return destinationRepo.findAll();
    }

    public Optional<Destination> getDestinationById(UUID id) {
        return destinationRepo.findById(id);
    }

    public Optional<Destination> updateDestination(UUID id, Destination newDestination) {
        Optional<Destination> foundDestination = destinationRepo.findById(id);
        if (foundDestination.isEmpty())
            return Optional.empty();
        Destination existingDestination = foundDestination.get();
        existingDestination.setSiteNumber(newDestination.getSiteNumber());
        existingDestination.setName(newDestination.getName());
        Destination savedDestination = destinationRepo.save(existingDestination);
        return Optional.of(savedDestination);
    }

    public String deleteDestination(UUID id) {
        Optional<Destination> foundDestination = destinationRepo.findById(id);
        if (foundDestination.isEmpty())
            return "The destination with that id does not exist";
        if (tourGuideRepo.existsByDestination(foundDestination.get()))
            return "That destination is taken by a tour guide, move the tour guide first";
        destinationRepo.deleteById(id);
        return "Destination deleted successfully";
    }
}
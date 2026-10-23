package rw.mukerarugendo.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tour_guide")
public class TourGuide {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "languages")
    private String languages;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @ManyToMany
    @JoinTable(
            name = "tour_guide_tour_type",
            joinColumns = @JoinColumn(name = "tour_guide_id"),
            inverseJoinColumns = @JoinColumn(name = "tour_type_id")
    )
    private List<TourType> tourTypes = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public List<TourType> getTourTypes() {
        return tourTypes;
    }

    public void setTourTypes(List<TourType> tourTypes) {
        this.tourTypes = tourTypes;
    }
}
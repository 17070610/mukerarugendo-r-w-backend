package rw.mukerarugendo.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tour_booking")
public class TourBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tour_date")
    private LocalDate tourDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TourStatus status;

    @Column(name = "group_size")
    private int groupSize;

    @ManyToOne
    @JoinColumn(name = "traveller_id")
    private Traveller traveller;

    @ManyToOne
    @JoinColumn(name = "tour_guide_id")
    private TourGuide tourGuide;

    public UUID getId() {
        return id;
    }

    public LocalDate getTourDate() {
        return tourDate;
    }

    public void setTourDate(LocalDate tourDate) {
        this.tourDate = tourDate;
    }

    public TourStatus getStatus() {
        return status;
    }

    public void setStatus(TourStatus status) {
        this.status = status;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public Traveller getTraveller() {
        return traveller;
    }

    public void setTraveller(Traveller traveller) {
        this.traveller = traveller;
    }

    public TourGuide getTourGuide() {
        return tourGuide;
    }

    public void setTourGuide(TourGuide tourGuide) {
        this.tourGuide = tourGuide;
    }
}

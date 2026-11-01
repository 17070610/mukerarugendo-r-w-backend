package rw.mukerarugendo.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tour_feedback")
public class TourFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "highlight")
    private String highlight;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private int rating;

    @Column(name = "submitted_date")
    private LocalDate submittedDate;

    @ManyToOne
    @JoinColumn(name = "tour_booking_id")
    private TourBooking tourBooking;

    public UUID getId() {
        return id;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDate submittedDate) {
        this.submittedDate = submittedDate;
    }

    public TourBooking getTourBooking() {
        return tourBooking;
    }

    public void setTourBooking(TourBooking tourBooking) {
        this.tourBooking = tourBooking;
    }
}

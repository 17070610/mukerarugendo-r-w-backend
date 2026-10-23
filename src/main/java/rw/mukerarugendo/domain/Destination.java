package rw.mukerarugendo.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "destination")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "site_number")
    private int siteNumber;

    @Column(name = "name")
    private String name;

    public UUID getId() {
        return id;
    }

    public int getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(int siteNumber) {
        this.siteNumber = siteNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

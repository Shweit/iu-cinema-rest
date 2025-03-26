package com.shweit.cinema.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hall")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hallId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private int totalRows;

    @Column(columnDefinition = "JSON")
    private String seatPlacement;

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public String getSeatPlacement() {
        return seatPlacement;
    }

    public void setSeatPlacement(String seatPlacement) {
        this.seatPlacement = seatPlacement;
    }
}

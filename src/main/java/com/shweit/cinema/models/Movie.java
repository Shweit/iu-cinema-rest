package com.shweit.cinema.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;

    @Column(nullable = false)
    @NotBlank(message = "Movie name is required")
    @Size(min = 1, max = 255, message = "Movie name must be between 1 and 255 characters")
    private String name;

    @Column(name = "short_desc", columnDefinition = "TEXT")
    @NotBlank(message = "Short description is required")
    @Size(max = 500, message = "Short description must not exceed 500 characters")
    private String shortDesc;
    
    private String cover;

    @Column(columnDefinition = "TEXT", name="description")
    private String description;

    @NotNull(message = "Playtime is required")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message = "Playtime must be in format HH:mm:ss")
    private String playtime;

    @NotNull(message = "Release date is required")
    @PastOrPresent(message = "Release date must be in present or past")
    private Date releaseDate;

    @Column(columnDefinition = "JSON")
    private String broadcastingTimes;

    @Column(columnDefinition = "JSON")
    private String topCast;

    private String trailerUrl;

    private String director;

    @Column(columnDefinition = "JSON")
    private String genre;

    @Column
    @NotNull(message = "Rating is required")
    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 5, message = "Rating must not exceed 5")
    private float rating;

    private float price;

    @ManyToOne
    @JoinColumn(name = "hallId", nullable = false)
    private Hall hall;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDesc(String description) {
        this.description = description;
    }

    public String getPlaytime() {
        return playtime;
    }

    public void setPlaytime(String playtime) {
        this.playtime = playtime;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBroadcastingTimes() {
        return broadcastingTimes;
    }

    public void setBroadcastingTimes(String broadcastingTimes) {
        this.broadcastingTimes = broadcastingTimes;
    }

    public String getTopCast() {
        return topCast;
    }

    public void setTopCast(String topCast) {
        this.topCast = topCast;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}

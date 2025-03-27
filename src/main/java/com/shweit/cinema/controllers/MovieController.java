package com.shweit.cinema.controllers;

import com.shweit.cinema.models.Movie;
import com.shweit.cinema.models.Hall;
import com.shweit.cinema.repositories.MovieRepository;
import com.shweit.cinema.repositories.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private HallRepository hallRepository;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = (List<Movie>) movieRepository.findAll();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody Map<String, Object> requestBody) {
        // Validate movie ID
        if (requestBody.containsKey("movieId")) {
            return ResponseEntity.badRequest().body("Movie ID should not be provided");
        }
        
        // Validate required fields
        if (!requestBody.containsKey("name") || requestBody.get("name") == null || requestBody.get("name").toString().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name is required");
        }
        
        // Validate numeric fields
        ResponseEntity<?> priceValidation = validateNumericField(requestBody, "price", "Price", true);
        if (priceValidation != null) {
            return priceValidation;
        }
        
        ResponseEntity<?> ratingValidation = validateNumericField(requestBody, "rating", "Rating", false);
        if (ratingValidation != null) {
            return ratingValidation;
        }

        // Create Movie entity
        Movie movie = new Movie();
        
        // Set string fields
        setStringField(movie, requestBody, "name", movie::setName, true);
        setStringField(movie, requestBody, "shortDesc", movie::setShortDesc, false);
        setStringField(movie, requestBody, "cover", movie::setCover, false);
        setStringField(movie, requestBody, "description", movie::setDesc, false);
        setStringField(movie, requestBody, "playtime", movie::setPlaytime, false);
        setStringField(movie, requestBody, "broadcastingTimes", movie::setBroadcastingTimes, false);
        setStringField(movie, requestBody, "topCast", movie::setTopCast, false);
        setStringField(movie, requestBody, "trailerUrl", movie::setTrailerUrl, false);
        setStringField(movie, requestBody, "director", movie::setDirector, false);
        setStringField(movie, requestBody, "genre", movie::setGenre, false);
        
        // Set date field
        if (requestBody.containsKey("releaseDate") && requestBody.get("releaseDate") != null) {
            try {
                movie.setReleaseDate(java.sql.Date.valueOf(requestBody.get("releaseDate").toString()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid release date format. Use YYYY-MM-DD");
            }
        }
        
        // Set numeric fields
        setNumericField(movie, requestBody, "rating", movie::setRating, Float::parseFloat);
        setNumericField(movie, requestBody, "price", movie::setPrice, Float::parseFloat);

        // Handle hall assignment
        ResponseEntity<?> hallAssignmentResult = assignHallToMovie(movie, requestBody);
        if (hallAssignmentResult != null) {
            return hallAssignmentResult;
        }

        // Save movie
        try {
            Movie savedMovie = movieRepository.save(movie);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating movie: " + e.getMessage());
        }
    }
    
    /**
     * Validates a numeric field in the request body
     */
    private ResponseEntity<?> validateNumericField(Map<String, Object> requestBody, String fieldName, 
                                                 String displayName, boolean mustBePositive) {
        if (requestBody.containsKey(fieldName)) {
            try {
                float value = Float.parseFloat(requestBody.get(fieldName).toString());
                if (mustBePositive && value <= 0) {
                    return ResponseEntity.badRequest().body(displayName + " must be positive");
                }
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(displayName + " must be a valid number");
            }
        }
        return null;
    }
    
    /**
     * Sets a string field in the movie object
     */
    private void setStringField(Movie movie, Map<String, Object> requestBody, String fieldName, 
                               java.util.function.Consumer<String> setter, boolean required) {
        if (required || requestBody.containsKey(fieldName)) {
            setter.accept(requestBody.get(fieldName) != null ? requestBody.get(fieldName).toString() : null);
        }
    }
    
    /**
     * Sets a numeric field in the movie object
     */
    private <T> void setNumericField(Movie movie, Map<String, Object> requestBody, String fieldName,
                                   java.util.function.Consumer<T> setter, java.util.function.Function<String, T> parser) {
        if (requestBody.containsKey(fieldName) && requestBody.get(fieldName) != null) {
            try {
                setter.accept(parser.apply(requestBody.get(fieldName).toString()));
            } catch (NumberFormatException e) {
                // Validation already done, ignore exception
            }
        }
    }
    
    /**
     * Assigns a hall to the movie
     */
    private ResponseEntity<?> assignHallToMovie(Movie movie, Map<String, Object> requestBody) {
        Integer hallId = null;
        if (requestBody.containsKey("hallId") && requestBody.get("hallId") != null) {
            try {
                hallId = Integer.parseInt(requestBody.get("hallId").toString());
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Hall ID must be a valid number");
            }
        }
        
        if (hallId != null && hallId != 0) {
            Optional<Hall> requestedHall = hallRepository.findById(hallId);
            if (requestedHall.isEmpty()) {
                return ResponseEntity.badRequest().body("Hall with ID " + hallId + " not found");
            }
            movie.setHall(requestedHall.get());
        } else {
            // If no specific hall is requested, assign the default one
            Hall defaultHall = hallRepository.findFirstByOrderByHallIdAsc();
            if (defaultHall == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No hall available for movie assignment");
            }
            movie.setHall(defaultHall);
        }
        
        return null;
    }
}
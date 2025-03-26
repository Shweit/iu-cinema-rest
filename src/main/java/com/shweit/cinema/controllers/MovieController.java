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
import java.util.HashMap;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

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
    public ResponseEntity<?> createMovie(@Valid @RequestBody Movie movie, BindingResult bindingResult) {
        if (movie.getMovieId() != 0) {
            return ResponseEntity.badRequest().body("Movie ID should not be provided");
        }
        
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        // Handle hall assignment using hallId
        Integer hallId = movie.getHall() != null ? movie.getHall().getHallId() : null;
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

        try {
            Movie savedMovie = movieRepository.save(movie);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating movie: " + e.getMessage());
        }
    }
}
package com.shweit.cinema.repositories;

import com.shweit.cinema.models.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
    
}
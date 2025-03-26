package com.shweit.cinema.repositories;

import com.shweit.cinema.models.Hall;
import org.springframework.data.repository.CrudRepository;

public interface HallRepository extends CrudRepository<Hall, Integer> {
    Hall findFirstByOrderByHallIdAsc();
}
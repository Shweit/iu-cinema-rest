/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shweit.cinema.repositories;

import com.shweit.cinema.models.Ticket;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Dennis
 */
public interface TicketRepository extends CrudRepository<Ticket, Integer> {
    
}

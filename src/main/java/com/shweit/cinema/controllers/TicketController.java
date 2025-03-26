/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shweit.cinema.controllers;

import com.shweit.cinema.models.Ticket;
import com.shweit.cinema.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepo;
    
    @GetMapping
    public @ResponseBody Iterable<Ticket> getAllTickets() {
        return ticketRepo.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable("id") int id) {
        Optional<Ticket> ticket = ticketRepo.findById(id);
        if (ticket.isPresent()) {
            return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

package com.alibou.security.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Ticket findTicketById(int id);

    @Query("select distinct(t) from Ticket t where t.isBooked = false and t.timeOfDeparture between :lowerBound and :upperBound")
    List<Ticket> findUniqueTicketsByTimeOfDepartureBetween(int lowerBound, int upperBound);
}

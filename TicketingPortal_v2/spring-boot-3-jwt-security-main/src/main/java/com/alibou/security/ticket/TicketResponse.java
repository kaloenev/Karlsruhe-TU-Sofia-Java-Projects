package com.alibou.security.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {

    private int ticketId;
    private String destination;
    private String timeOfDeparture;
    private double distance;
    private double price;
    public TicketResponse(Ticket ticket, double price, double distance) {
        ticketId = ticket.getId();
        Destination destination1 = ticket.getDestination();
        destination = destination1.getCity();
        this.price = price;
        this.distance = distance;
        int hours = ticket.getTimeOfDeparture() / 100;
        int minutes = ticket.getTimeOfDeparture() % 100;
        this.timeOfDeparture = hours + ":" + minutes + "0";
    }

    public TicketResponse(Ticket ticket) {
        ticketId = ticket.getId();
        Destination destination1 = ticket.getDestination();
        destination = destination1.getCity();
        this.price = ticket.getPrice();
        this.distance = ticket.getDistance();
        int hours = ticket.getTimeOfDeparture() / 100;
        int minutes = ticket.getTimeOfDeparture() % 100;
        this.timeOfDeparture = hours + ":" + minutes + "0";
    }
}

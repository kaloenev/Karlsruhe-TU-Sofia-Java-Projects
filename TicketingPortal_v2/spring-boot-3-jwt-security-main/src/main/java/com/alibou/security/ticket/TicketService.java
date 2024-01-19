package com.alibou.security.ticket;

import com.alibou.security.exceptionHandling.CustomException;
import com.alibou.security.user.User;
import com.alibou.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TicketService {

    private static final double PRICE_PER_KM = 0.25;

    private static final int DISCOUNTED_HOUR_MORNING = 930;

    private static final int DISCOUNTED_HOUR_NOON = 1600;

    private static final int DISCOUNTED_HOUR_EVENING = 1930;

    private final TicketRepository ticketRepository;

    private final DestinationRepo destinationRepo;

    private final UserRepository userRepository;

    public TicketResponse getTicket(int ticketId, String token, boolean isTravellingWithChild, int latitude, int longitude, boolean isRoundTrip) {
        User user = userRepository.findUserByTokens_token(token.substring(7));
        Ticket ticket = ticketRepository.findTicketById(ticketId);
        double distance = Math.sqrt(Math.pow(Math.abs(latitude - ticket.getDestination().getLatitude()), 2.0) + Math.pow(Math.abs(longitude
                - ticket.getDestination().getLongitude()), 2));
        BigDecimal bd = BigDecimal.valueOf(distance).setScale(2, RoundingMode.HALF_UP);
        distance = bd.doubleValue();
        double price = calculatePrice(ticket, distance, isTravellingWithChild, user.isHasAFamilyCard(), user.isHasAOver60sCard());
        if (isRoundTrip) price *= 2;
        return new TicketResponse(ticket, price, distance);
    }

    public void bookTicket(int ticketId, String token, boolean isTravellingWithChild, int latitude, int longitude, boolean isRoundTrip) {
        User user = userRepository.findUserByTokens_token(token.substring(7));
        Ticket ticket = ticketRepository.findTicketById(ticketId);
        double distance = Math.sqrt(Math.pow(Math.abs(latitude - ticket.getDestination().getLatitude()), 2.0) + Math.pow(Math.abs(longitude
                - ticket.getDestination().getLongitude()), 2));
        double price = calculatePrice(ticket, distance, isTravellingWithChild, user.isHasAFamilyCard(), user.isHasAOver60sCard());
        if (isRoundTrip) price *= 2;
        ticket.setUser(user);
        ticket.setBooked(true);
        ticket.setPrice(price);
        ticket.setDistance(distance);
        ticket.setRoundTrip(isRoundTrip);
        ticketRepository.save(ticket);
        user.deductBalance(price);
        user.addTicket(ticket);
        userRepository.save(user);
    }

    public List<TicketResponse> getTicketOptions(String timeLowerBound, String timeUpperBound, double distance, int latitude, int longitude) {
           List<TicketResponse> ticketResponses = new ArrayList<>();
           int lowerBound = Integer.parseInt(timeLowerBound.replace(":", ""));
           int upperBound = Integer.parseInt(timeUpperBound.replace(":", ""));
           List<Ticket> tickets = ticketRepository.findUniqueTicketsByTimeOfDepartureBetween(lowerBound, upperBound);
           for (Ticket ticket : tickets) {
               double distance1 = Math.sqrt(Math.pow(Math.abs(latitude - ticket.getDestination().getLatitude()), 2.0) + Math.pow(Math.abs(longitude
                       - ticket.getDestination().getLongitude()), 2));
               if (distance1 <= distance) {
                   BigDecimal bd = BigDecimal.valueOf(distance1).setScale(2, RoundingMode.HALF_UP);
                   distance1 = bd.doubleValue();
                   double price = distance1 * PRICE_PER_KM;
                   ticketResponses.add(new TicketResponse(ticket, price, distance1));
               }
           }
           return ticketResponses;
    }

    public List<TicketResponse> getTicketHistory(String token) {
        User user = userRepository.findUserByTokens_token(token.substring(7));
        List<TicketResponse> ticketResponses = new ArrayList<>();
        for (Ticket ticket : user.getTickets()) {
            TicketResponse ticketResponse = new TicketResponse(ticket);
            ticketResponses.add(ticketResponse);
        }
        return ticketResponses;
    }

    public void cancelTicket(String token, int id) throws CustomException {
        User user = userRepository.findUserByTokens_token(token.substring(7));
        Ticket ticket = ticketRepository.findTicketById(id);
        if (!user.getTickets().contains(ticket)) throw new CustomException(HttpStatus.NOT_FOUND, "You do not own this ticket or it does not exist");
        if (ticket.getTimeOfDeparture() < Integer.parseInt(new Timestamp(System.currentTimeMillis()).toString().substring(11, 15)
                .replace(":", ""))) throw new CustomException(HttpStatus.FORBIDDEN, "Ticket has already expired");
        user.removeTicket(ticket);
        user.setBalance(user.getBalance() + ticket.getPrice());
        ticket.setUser(null);
        userRepository.save(user);
        ticketRepository.save(ticket);
    }

    private double calculatePrice(Ticket ticket, double distance, boolean isTravellingWithChild, boolean hasAFamilyCard, boolean hasAOver60sCard) {
        double price = distance * PRICE_PER_KM;
        boolean hoursOfDepartureDiscount = (ticket.getTimeOfDeparture() > DISCOUNTED_HOUR_MORNING
                && ticket.getTimeOfDeparture() < DISCOUNTED_HOUR_NOON) || ticket.getTimeOfDeparture() > DISCOUNTED_HOUR_EVENING;
        if (hoursOfDepartureDiscount) {
            price *= 0.95;
        }

        if (hasAFamilyCard && isTravellingWithChild) {
            price *= 0.5;
        }
        else if (hasAOver60sCard) {
            price *= 0.66;
            if (isTravellingWithChild) {
                price *= 0.9;
            }
        }
        else if (isTravellingWithChild) {
            price *= 0.9;
        }
        BigDecimal bd = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
        price = bd.doubleValue();
        return price;
    }

    public void addTicket(int longitude, int latitude, String destination, int amount, int timeOfDeparture) {
        Destination destination1 = Destination.builder().latitude(latitude).longitude(longitude).city(destination).tickets(new ArrayList<>()).build();
        destinationRepo.save(destination1);
        for (int i = 0; i < amount; i++) {
            Ticket ticket = Ticket.builder().destination(destination1).timeOfDeparture(timeOfDeparture).user(null).build();
            ticketRepository.save(ticket);
            destination1.addTicket(ticket);
            destinationRepo.save(destination1);
        }
    }


}

package com.alibou.security.ticket;
import com.alibou.security.user.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DestinationRepo destinationRepo;
    @InjectMocks
    private TicketService ticketService;

    private User user;

    @BeforeEach
    public void setup(){
        ticketRepository = Mockito.mock(TicketRepository.class);
        destinationRepo = Mockito.mock(DestinationRepo.class);
        userRepository = Mockito.mock(UserRepository.class);

        ticketService = new TicketService(ticketRepository, destinationRepo, userRepository);
        user = User.builder()
                .id(1)
                .firstname("Kaloyan")
                .lastname("Enev")
                .email("1234@gmail.com")
                .password("1234")
                .tickets(new ArrayList<>())
                .balance(150)
                .build();
    }


    @Test
    void getTicketOptions() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(Ticket.builder().destination(Destination.builder().city("Sofia").longitude(80).latitude(140).id(1).build())
                .timeOfDeparture(2200).id(1).build());
        tickets.add(Ticket.builder().destination(Destination.builder().city("Varna").longitude(550).latitude(400).id(2).build())
                .id(2).timeOfDeparture(2300).build());
        tickets.add(Ticket.builder().destination(Destination.builder().city("Plovdiv").longitude(200).latitude(120).id(3).build())
                .id(3).timeOfDeparture(2300).build());
        given(ticketRepository.findUniqueTicketsByTimeOfDepartureBetween(2100, 2330)).willReturn(tickets);
        List<TicketResponse> ticketResponses = ticketService.getTicketOptions("21:00", "23:30",
                300, 30, 170);
        assertThat(ticketResponses.get(0).getDestination()).isEqualTo("Sofia");
        assertThat(ticketResponses.get(1).getDestination()).isEqualTo("Plovdiv");
    }

    @Test
    void bookTicketWithDiscount() {
        when(userRepository.findUserByTokens_token("token")).thenReturn(user);
        Ticket ticket = Ticket.builder().destination(Destination.builder().city("Sofia").longitude(80).latitude(140).id(1).build())
                .timeOfDeparture(1400).id(1).build();
        when(ticketRepository.findTicketById(1)).thenReturn(ticket);
        ticketService.bookTicket(1, "Bearer token", false, 30, 170, false);
        assertThat(ticket.getPrice()).isEqualTo(33.76);
        assertThat(ticket.isBooked()).isTrue();
        assertThat(user.getBalance()).isEqualTo(116.24);
    }

    @Test
    void bookTicketWithDiscountRoundTrip() {
        when(userRepository.findUserByTokens_token("token")).thenReturn(user);
        Ticket ticket = Ticket.builder().destination(Destination.builder().city("Sofia").longitude(80).latitude(140).id(1).build())
                .timeOfDeparture(2200).id(1).build();
        when(ticketRepository.findTicketById(1)).thenReturn(ticket);
        ticketService.bookTicket(1, "Bearer token", false, 30, 170, true);
        assertThat(ticket.getPrice()).isEqualTo(67.51);
        assertThat(ticket.isBooked()).isTrue();
        assertThat(user.getBalance()).isEqualTo(82.49);
    }

    @Test
    void bookTicketWithoutDiscountWithFamilyCardAndChild() {
        when(userRepository.findUserByTokens_token("token")).thenReturn(user);
        Ticket ticket = Ticket.builder().destination(Destination.builder().city("Sofia").longitude(80).latitude(140).id(1).build())
                .timeOfDeparture(830).id(1).build();
        when(ticketRepository.findTicketById(1)).thenReturn(ticket);
        user.setHasAFamilyCard(true);
        ticketService.bookTicket(1, "Bearer token", true, 30, 170, false);
        assertThat(ticket.getPrice()).isEqualTo(17.77);
    }

    @Test
    void bookTicketWithDiscountWithChildWithOver60sCard() {
        when(userRepository.findUserByTokens_token("token")).thenReturn(user);
        Ticket ticket = Ticket.builder().destination(Destination.builder().city("Sofia").longitude(80).latitude(140).id(1).build())
                .timeOfDeparture(1400).id(1).build();
        user.setHasAOver60sCard(true);
        when(ticketRepository.findTicketById(1)).thenReturn(ticket);
        ticketService.bookTicket(1, "Bearer token", true, 30, 170, false);
        assertThat(ticket.getPrice()).isEqualTo(20.05);
    }

    @Test
    void bookTicketWithoutDiscountWithOver60sCardNoChild() {
        when(userRepository.findUserByTokens_token("token")).thenReturn(user);
        Ticket ticket = Ticket.builder().destination(Destination.builder().city("Sofia").longitude(80).latitude(140).id(1).build())
                .timeOfDeparture(1700).id(1).build();
        user.setHasAOver60sCard(true);
        when(ticketRepository.findTicketById(1)).thenReturn(ticket);
        ticketService.bookTicket(1, "Bearer token", false, 30, 170, false);
        assertThat(ticket.getPrice()).isEqualTo(23.45);
    }




}

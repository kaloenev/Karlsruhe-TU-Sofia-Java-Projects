package com.alibou.security.ticket;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_destination")
public class Destination {

    @Id
    @GeneratedValue
    private Integer id;
    private int latitude;
    private int longitude;
    private String city;

    @OneToMany(mappedBy = "destination")
    @ToString.Exclude
    private List<Ticket> tickets = new ArrayList<>();

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }
}

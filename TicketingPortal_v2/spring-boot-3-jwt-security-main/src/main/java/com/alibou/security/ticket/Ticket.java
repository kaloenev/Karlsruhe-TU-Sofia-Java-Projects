package com.alibou.security.ticket;

import com.alibou.security.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_ticket")
public class Ticket {

    @Id
    @GeneratedValue
    private Integer id;

    private int timeOfDeparture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    private double price = 0;

    private double distance = 0;

    private boolean isRoundTrip = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isBooked = false;
}

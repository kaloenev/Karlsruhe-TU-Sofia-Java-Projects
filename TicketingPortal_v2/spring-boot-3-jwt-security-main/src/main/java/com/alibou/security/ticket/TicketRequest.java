package com.alibou.security.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {
    int ticketId;
    boolean isTravellingWithChild;
    int latitude;
    int longitude;
    boolean isRoundTrip;
}

package com.alibou.security.ticket;

import com.alibou.security.exceptionHandling.CustomException;
import com.alibou.security.exceptionHandling.CustomWarning;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/filterTickets")
    public ResponseEntity<Object> filterTickets(
            @RequestBody FilterRequest request
    ) {
        return ResponseEntity.ok(ticketService.getTicketOptions(request.getTimeLowerBound(), request.getTimeUpperBound(),
                request.getDistance(), request.getLatitude(), request.getLongitude()));
    }

    @GetMapping("/getTicketHistory")
    public ResponseEntity<Object> getTicketHistory(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(ticketService.getTicketHistory(request.getHeader("Authorization")));
    }

    @GetMapping("/cancelTicket/{id}")
    public ResponseEntity<Object> cancelTicket(
            @PathVariable int id, HttpServletRequest httpServletRequest
    ) {
        try {
            ticketService.cancelTicket(httpServletRequest.getHeader("Authorization"), id);
        } catch (CustomException e) {
            CustomWarning warning = new CustomWarning(HttpStatus.CONFLICT, e.getMessage());
            return new ResponseEntity<>(warning, new HttpHeaders(), warning.getStatus());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/getTicket")
    public ResponseEntity<Object> getTicket(
            @RequestBody TicketRequest request,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(ticketService.getTicket(request.getTicketId(), httpServletRequest.getHeader("Authorization"),
                request.isTravellingWithChild(), request.getLatitude(), request.getLongitude(), request.isRoundTrip()));
    }

    @PostMapping("/bookTicket")
    public void bookTicket(
            @RequestBody TicketRequest request,
            HttpServletRequest httpServletRequest
    ) {
        ticketService.bookTicket(request.getTicketId(), httpServletRequest.getHeader("Authorization"),
                request.isTravellingWithChild(), request.getLatitude(), request.getLongitude(), request.isRoundTrip());
    }

}

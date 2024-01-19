package com.alibou.security;

import com.alibou.security.auth.AuthenticationService;
import com.alibou.security.auth.RegisterRequest;
import com.alibou.security.ticket.TicketService;
import com.alibou.security.user.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.alibou.security.user.Role.ADMIN;
import static com.alibou.security.user.Role.MANAGER;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(
//			AuthenticationService service,
//			TicketService ticketservice
//	) {
//		return args -> {
//			for (int i = 0; i <= 2300; i+=100) {
//				ticketservice.addTicket(80, 140, "Sofia", 20, i);
//				ticketservice.addTicket(200, 120, "Plovdiv", 20, i);
//				ticketservice.addTicket(350, 500, "Varna", 20, i);
//				ticketservice.addTicket(170, 480, "Burgas", 20, i);
//				ticketservice.addTicket(70, 60, "Blagoevgrad", 20, i);
//			}
//
//		};
//	}
}

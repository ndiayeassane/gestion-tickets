package net.ennov.gestion_ticket.utils;

import net.ennov.gestion_ticket.entities.Ticket;
import net.ennov.gestion_ticket.entities.User;
import net.ennov.gestion_ticket.enumeration.Status;

public class Data {

	public static User createUser() {
		
		return new User(1L,"ass","ass@extra.com", null);
	}
	
	public static Ticket createTicket() {
		
		User user = createUser();
		
		return Ticket.builder()
				.id(1L)
				.titre("aaa")
				.description("description")
				.status(Status.ENCOURS)
				.user(user)
				.build();
	}
}

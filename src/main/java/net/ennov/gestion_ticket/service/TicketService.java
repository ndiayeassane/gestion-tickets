package net.ennov.gestion_ticket.service;

import java.util.Collection;
import java.util.Optional;

import net.ennov.gestion_ticket.entities.Ticket;

public interface TicketService {

	/**
	 * Service interface get all tickets
	 * @return collection Ticket
	 */
	Collection<Ticket> getAllTickets();
	
	/**
	 * Service interface get one ticket
	 * @param id
	 * @return optional Ticket
	 */
	Optional<Ticket> getTicketById(Long id);
	
	/**
	 * Service interface create ticket
	 * @param ticket
	 * @return Ticket
	 */
	Ticket createTicket(Ticket ticket);
	
	/**
	 * Service interface update ticket
	 * @param id
	 * @param ticket
	 * @return Ticket
	 */
	Ticket updateTicket(Long id,Ticket ticket);
	
	/**
	 * Service interface Assign ticket to user
	 * @param idUser
	 * @param idTicket
	 * @return void
	 */
	Ticket assignTicketToUser(Long idUser,Long idTicket);
	
	/**
	 * Service interface delete ticket
	 * @param id
	 * @return void
	 */
	void deleteTicket(Long id);
}

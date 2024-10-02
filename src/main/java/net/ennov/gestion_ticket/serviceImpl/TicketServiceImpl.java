package net.ennov.gestion_ticket.serviceImpl;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.ennov.gestion_ticket.entities.Ticket;
import net.ennov.gestion_ticket.entities.User;
import net.ennov.gestion_ticket.exception.ApiRequestException;
import net.ennov.gestion_ticket.repository.TicketRepository;
import net.ennov.gestion_ticket.repository.UserRepository;
import net.ennov.gestion_ticket.service.TicketService;
import net.ennov.gestion_ticket.utils.Constants;

/**
 * Service Implement entity tickets
 * @author Assane Ndiaye
 *
 */
@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService{
	
	private final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);
	
	private final TicketRepository ticketRepository;
	
	private final UserRepository userRepository;
	
	/**
	 * Service Implement request get all tickets
	 * @return Collection Ticket
	 */
	@Override
	public Collection<Ticket> getAllTickets() {
		
		return ticketRepository.findAll()
				.stream()
				.collect(Collectors.toList());
	}

	/**
	 * Service Implement request get one ticket by id
	 * @param id
	 * @return Optional Ticket
	 */
	@Override
	public Optional<Ticket> getTicketById(Long id) {
		
		if(id == null) throw new ApiRequestException(Constants.NO_ID_FOUND);
		
		Optional<Ticket> optionalTicket = ticketRepository.findById(id);
		
		if(!optionalTicket.isPresent()) throw new ApiRequestException(Constants.NO_DATA);
		
		return optionalTicket;
		
				
		
	}
	/**
	 * Service implement request to create ticket
	 * @param ticket
	 * @return Ticket
	 */
	@Override
	public Ticket createTicket(Ticket ticket) {
		
		return ticketRepository.save(ticket);
	}

	/**
	 * Service implement request to update ticket
	 * @param ticket
	 * @param id
	 * @return Ticket
	 */
	@Override
	public Ticket updateTicket(Long id,Ticket ticket) {
		
		if(id == null)  throw new ApiRequestException(Constants.NO_ID_FOUND);
		
		Optional<Ticket> optionalTicket = ticketRepository.findById(id);
		
		if(optionalTicket.isPresent()) {
			
			return ticketRepository.save(ticket);
		}
		else {
			throw new ApiRequestException(Constants.NO_DATA);
		}
		
	}
	/**
	 * Service implement request to assign ticket to user
	 * @param idUser
	 * @param idTicket
	 * @return void
	 */
	@Override
	public Ticket assignTicketToUser(Long idUser, Long idTicket) {
		
		Ticket ticket = this.getTicketById(idTicket).get();
		
		User user = userRepository.findById(idUser).get();
		
		ticket.setUser(user);
		
		return updateTicket(idTicket, ticket);
		
	}

	/**
	 * Service implement request to delete ticket
	 * @param id
	 * @return void
	 */
	@Override
	public void deleteTicket(Long id) {
		
		if(id == null)  throw new ApiRequestException(Constants.NO_ID_FOUND);
		
		Optional<Ticket> optionalTicket = ticketRepository.findById(id);
		
		if(optionalTicket.isPresent()) {
			
			 ticketRepository.deleteById(id);
		}
		else {
			throw new ApiRequestException(Constants.NO_DATA);
		}
		
	}

}

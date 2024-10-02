package net.ennov.gestion_ticket.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.ennov.gestion_ticket.entities.Ticket;
import net.ennov.gestion_ticket.service.TicketService;

@RestController
@AllArgsConstructor
public class TicketController {

	private final TicketService ticketService;
	
	
	@GetMapping("/tickets")
	public ResponseEntity<Collection<Ticket>> getAllTickets(){
		
		return ResponseEntity.ok().body(ticketService.getAllTickets());
	}
	
	@GetMapping("/tickets/{id}")
	public ResponseEntity<Ticket> getAllTickets(@PathVariable("id")Long id){
		
		return ResponseEntity.ok().body(ticketService.getTicketById(id).get());
	}
	

	@PostMapping("/tickets")
	public ResponseEntity<Ticket> saveTicket(@RequestBody Ticket ticket){
		
		return ResponseEntity.ok().body(ticketService.createTicket(ticket));
	}
	
	@PutMapping("/tickets/{id}")
	public ResponseEntity<Ticket> updateTicket(@PathVariable("id")Long id, @RequestBody Ticket ticket){
		
		return ResponseEntity.ok().body(ticketService.updateTicket(id, ticket));
	}
	
	@PutMapping("/tickets/{id}/assign/{userId}")
	public ResponseEntity<Ticket> assignTicketToUser(@PathVariable("id")Long id, @PathVariable("userId")Long userId){
		
		ticketService.assignTicketToUser(userId, id);
		
		return ResponseEntity.ok().body(ticketService.assignTicketToUser(userId, id));
	}
	
	@DeleteMapping("/tickets/{id}")
	public ResponseEntity<?> deleteTicket(@PathVariable("id")Long id){
		
		ticketService.deleteTicket(id);
		
		return ResponseEntity.ok().body(HttpStatus.ACCEPTED);
	}
}

package net.ennov.gestion_ticket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ennov.gestion_ticket.entities.Ticket;
import net.ennov.gestion_ticket.entities.User;
import net.ennov.gestion_ticket.enumeration.Status;
import net.ennov.gestion_ticket.repository.TicketRepository;
import net.ennov.gestion_ticket.repository.UserRepository;
import net.ennov.gestion_ticket.serviceImpl.TicketServiceImpl;
import net.ennov.gestion_ticket.utils.Data;

/**
 * Testes unitaires des fonctionalites des services tickets
 * @author Assane Ndiaye
 *
 */
@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
	
	@InjectMocks
	private TicketServiceImpl ticketService;
	
	@Mock
	private TicketRepository ticketRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Test
	void should_return_ticket() {
		
		 //Given
        
        Ticket ticketEntity = Data.createTicket();

        Mockito.when(ticketRepository.findAll()).thenReturn((Arrays.asList(ticketEntity)));

        //When
        List<Ticket> ticketsReturn = ticketService.getAllTickets().stream().collect(Collectors.toList());

        //Then
        assertEquals(1, ticketsReturn.size(), "should return 1 ticket");
        assertEquals(ticketEntity, ticketsReturn.get(0), "should be equal");
	}
	
	@Test
	void should_return_one_ticket() {
		
		//Given
		Ticket ticketEntity = Data.createTicket();
		
		 Mockito.when(ticketRepository.findById(ticketEntity.getId())).thenReturn(Optional.of(ticketEntity));
		 
		Ticket  ticketToReturn = ticketService.getTicketById(ticketEntity.getId()).orElseThrow(()-> new IllegalStateException("Not Find Data"));
		
		//Then
		
		assertEquals(ticketEntity, ticketToReturn, "should be equal");
	}

    @Test
    void should_create_ticket(){
        //Given
        
    	 Ticket ticketEntity = Data.createTicket();

        Mockito.when(ticketRepository.save(any())).thenReturn(ticketEntity);
        
        //When
        Ticket ticketToReturn = ticketService.createTicket(ticketEntity);

        //Then
        assertEquals(ticketToReturn, ticketEntity, "should return the same object");
    }
    
    @Test
    void should_update_user() {
       
    	//Given
        
    	Ticket ticketEntity = Data.createTicket();
        
    	ticketEntity.setStatus(Status.TERMINER);

        Mockito.when(ticketRepository.save(ticketEntity)).thenReturn(ticketEntity);
        
        Mockito.when(ticketRepository.findById(ticketEntity.getId())).thenReturn(Optional.of(ticketEntity));
        
        //When
        Ticket ticketToReturn = ticketService.updateTicket(ticketEntity.getId(),ticketEntity);

        //Then
        assertThat(ticketToReturn).isNotNull();
    }
    
    
    @Test
    void should_delete_ticket() {
       
    	//Given
    	Ticket ticketEntity1 = Data.createTicket();   	
        
        Mockito.when(ticketRepository.findById(ticketEntity1.getId())).thenReturn(Optional.of(ticketEntity1));

        //When
        ticketService.deleteTicket(ticketEntity1.getId());

        //Then
        assertAll(()->ticketService.deleteTicket(ticketEntity1.getId()));
    	
    }
    
    @Test
    void should_assign_ticket_to_user() {
    	
    	//Given
        
    	Ticket ticketEntity = Data.createTicket();
    	User userEntity = Data.createUser();
        
    	
        Mockito.when(ticketRepository.save(ticketEntity)).thenReturn(ticketEntity);
        
        Mockito.when(ticketRepository.findById(ticketEntity.getId())).thenReturn(Optional.of(ticketEntity));
        
        Mockito.when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        
        //When
        Ticket ticketToReturn  = ticketService.assignTicketToUser(userEntity.getId(), ticketEntity.getId());
         
         //Ticket ticketToReturn = ticketRepository.findById(ticketEntity.getId()).get();

        //Then
        assertThat(ticketToReturn.getUser()).isNotNull();
    }
    
    

	
}

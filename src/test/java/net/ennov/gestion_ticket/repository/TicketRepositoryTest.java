package net.ennov.gestion_ticket.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ennov.gestion_ticket.entities.Ticket;
import net.ennov.gestion_ticket.utils.Data;

/**
 * Testes unitaires d'access au donnees des repository tickets
 * @author Assane Ndiaye
 *
 */

@ExtendWith(MockitoExtension.class)
public class TicketRepositoryTest {
	
	@Mock
	TicketRepository ticketRepository;

	//TicketService ticketService;
	
	@Test
	void should_return_tickets() {
		
		 //Given
        
        Ticket ticketEntity = Data.createTicket();

        Mockito.when(ticketRepository.findAll()).thenReturn((Arrays.asList(ticketEntity)));

        //When
        List<Ticket> tickets = ticketRepository.findAll();

        //Then
        assertEquals(1, tickets.size(), "should return 1 user");
        assertEquals(ticketEntity, tickets.get(0), "should be equal");
	}
	

    @Test
    void should_create_ticket(){
        //Given
        
    	Ticket ticketEntity = Data.createTicket();

        Mockito.when(ticketRepository.save(any())).thenReturn(ticketEntity);
      


        //When
        Ticket ticketToReturn = ticketRepository.save(new Ticket());

        //Then
        assertEquals(ticketToReturn, ticketEntity, "should return the same object");
    }
    
    @Test
    void should_get_ticket() {
       
    	//Given
        
    	Ticket ticketEntity1 = Data.createTicket();
        
        Mockito.when(ticketRepository.findById(ticketEntity1.getId())).thenReturn(Optional.of(ticketEntity1));
        


        //When
        Ticket ticketEntity2 = ticketRepository.findById(ticketEntity1.getId()).orElseThrow(()->new IllegalStateException("Not found"));
    	
    	//then
    	assertEquals(ticketEntity1, ticketEntity2, "should return the same object");;
    }
    
   
    
  

}

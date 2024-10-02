package net.ennov.gestion_ticket.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.ennov.gestion_ticket.entities.Ticket;
import net.ennov.gestion_ticket.entities.User;
import net.ennov.gestion_ticket.enumeration.Status;
import net.ennov.gestion_ticket.service.TicketService;
import net.ennov.gestion_ticket.service.UserService;
import net.ennov.gestion_ticket.utils.Data;

/**
 * @author Assane Ndiaye
 *
 */

@WebMvcTest(controllers = TicketController.class)
@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private TicketService ticketService;
	
	@MockBean
	private UserService userService;
	
	
	@Test
	void should_return_tickets() throws Exception {
		
		 //Given
        
        Ticket ticketEntity = Data.createTicket();

        Mockito.when(ticketService.getAllTickets()).thenReturn((Arrays.asList(ticketEntity)));

        //When
        List<Ticket> ticketList = ticketService.getAllTickets().stream().collect(Collectors.toList());

        // HTTP POST request to get tickets
        ResultActions response = mockMvc.perform(get("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketEntity))
              );


        //  Response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(ticketList.size())));
	}
	
	@Test
	void should_create_ticket() throws JsonProcessingException, Exception {
		
		Ticket ticketEntity = Data.createTicket();
		
		BDDMockito.given(ticketService.createTicket(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

		    // HTTP POST request to create tickets
		
		    ResultActions response = mockMvc.perform(post("/tickets")
		        
		    		.contentType(MediaType.APPLICATION_JSON)
		        
		    		.content(objectMapper.writeValueAsString(ticketEntity)));
		        
		    //  Response expectations
		    response.andExpect(MockMvcResultMatchers.status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.titre", CoreMatchers.is(ticketEntity.getTitre())))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(ticketEntity.getDescription())));
		}
	
	@Test
	void should_update_ticket() throws JsonProcessingException, Exception {
		
		Ticket ticketEntity = Data.createTicket();
		 
		 ticketEntity.setStatus(Status.TERMINER);
		 
		 Mockito.when(ticketService.updateTicket(ticketEntity.getId(),ticketEntity)).thenReturn(ticketEntity);
		;
        
		    ResultActions response = mockMvc.perform(put("/tickets/1")
		       
		    		.contentType(MediaType.APPLICATION_JSON)
		        
		    		.content(objectMapper.writeValueAsString(ticketEntity)));
		        

		    response.andExpect(MockMvcResultMatchers.status().isOk());
	      
		    
		}
	
	@Test
	void should_delete_ticket() throws Exception {
		
		 Ticket ticketEntity = Data.createTicket();
		
		 doNothing().when(ticketService).deleteTicket(ticketEntity.getId());

		  // HTTP DELETE request 
		 
		  ResultActions response = mockMvc.perform(delete("/tickets/1")
		          .contentType(MediaType.APPLICATION_JSON));

		  // Response expectations
		  response.andExpect(MockMvcResultMatchers.status().isOk())
		  ;
	}
	
	@Test
	void should_assign_ticket_to_user() throws Exception {
		
		 
    	Ticket ticketEntity = Data.createTicket();
    	User userEntity = Data.createUser();
        
    	
        Mockito.when(ticketService.createTicket(ticketEntity)).thenReturn(ticketEntity);
        
        Mockito.when(ticketService.getTicketById(ticketEntity.getId())).thenReturn(Optional.of(ticketEntity));
        
        //Mockito.when(userService.(userEntity.getId())).thenReturn(Optional.of(userEntity));
        
        
        Mockito.when(ticketService.assignTicketToUser(userEntity.getId(), ticketEntity.getId())).thenReturn(ticketEntity);
         
         Ticket ticketToReturn = ticketService.getTicketById(ticketEntity.getId()).get();
         
         ResultActions response = mockMvc.perform(put("/tickets/1/assign/1")
		          .contentType(MediaType.APPLICATION_JSON)
		          .content(objectMapper.writeValueAsString(ticketToReturn))
		          )
        		 
        		 ;
         

		  // Response expectations
		  response.andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.jsonPath("$.titre", CoreMatchers.is(ticketEntity.getTitre())))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(ticketEntity.getDescription())));
		  ;
	}
}

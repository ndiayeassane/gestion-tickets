/**
 * 
 */
package net.ennov.gestion_ticket.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.ennov.gestion_ticket.entities.Ticket;
import net.ennov.gestion_ticket.entities.User;
import net.ennov.gestion_ticket.service.UserService;
import net.ennov.gestion_ticket.utils.Data;

/**
 * @author Assane Ndiaye
 *
 */
@WebMvcTest(controllers = UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserService userService;
	
	
	@Test
	void should_return_users() throws Exception {
		
		 //Given
        
        User userEntity = Data.createUser();

        Mockito.when(userService.getAllUsers()).thenReturn((Arrays.asList(userEntity)));

        //When
        List<User> users = userService.getAllUsers().stream().collect(Collectors.toList());

      
        ResultActions response = mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userEntity))
              );


        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(users.size())));
	}
	
	
	@Test
	void should_return_ticket_by_user() throws Exception {
		
		 //Given
        
        User userEntity = Data.createUser();
        
        Ticket ticketEntity = Data.createTicket();
        
        userEntity.setTickets(Arrays.asList(ticketEntity));
        
        Mockito.when(userService.getAllTicketsByUserId(userEntity.getId())).thenReturn((Arrays.asList(ticketEntity)));

        
       //When
       List<Ticket> listTicketsToReturn = userService.getAllTicketsByUserId(userEntity.getId())
       		.stream().collect(Collectors.toList());

      
        ResultActions response = mockMvc.perform(get("/users/1/ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userEntity))
              );


        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listTicketsToReturn.size())));
	}
	
	@Test
	void should_create_user() throws JsonProcessingException, Exception {
		
		User userEntity = Data.createUser();
		
		BDDMockito.given(userService.createUser(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

		    // HTTP POST request to create user
		    ResultActions response = mockMvc.perform(post("/users")
		        
		    		.contentType(MediaType.APPLICATION_JSON)
		        
		    		.content(objectMapper.writeValueAsString(userEntity)));
		        
		    //  Response expectations
		    response.andExpect(MockMvcResultMatchers.status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userEntity.getEmail())))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(userEntity.getUsername())));
		}
	
	@Test
	void should_update_user() throws JsonProcessingException, Exception {
		
		User userEntity = Data.createUser();
		
		userEntity.setUsername("oss");
		
		 Mockito.when(userService.updateUser(userEntity.getId(),userEntity)).thenReturn(userEntity);
		;

		    ResultActions response = mockMvc.perform(put("/users/1")
		       
		    		.contentType(MediaType.APPLICATION_JSON)
		        
		    		.content(objectMapper.writeValueAsString(userEntity)));
		        
	
		    response.andExpect(MockMvcResultMatchers.status().isOk())
		    		
		    ;
		}
	
	

}

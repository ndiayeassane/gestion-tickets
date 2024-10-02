package net.ennov.gestion_ticket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
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
import net.ennov.gestion_ticket.repository.TicketRepository;
import net.ennov.gestion_ticket.repository.UserRepository;
import net.ennov.gestion_ticket.serviceImpl.UserServiceImpl;
import net.ennov.gestion_ticket.utils.Data;

/**
 * Testes unitaires des fonctionalites des services utilisateurs
 * @author Assane Ndiaye
 *
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private TicketRepository ticketRepository;
	
	@Test
	void should_return_users() {
		
		 //Given
        
        User userEntity = Data.createUser();

        Mockito.when(userRepository.findAll()).thenReturn((Arrays.asList(userEntity)));

        //When
        List<User> users = userService.getAllUsers().stream().collect(Collectors.toList());

        //Then
        assertEquals(1, users.size(), "should return 1 user");
        assertEquals(userEntity, users.get(0), "should be equal");
	}
	

    @Test
    void should_create_user(){
        //Given
        
        User userEntity = Data.createUser();

        Mockito.when(userRepository.save(any())).thenReturn(userEntity);
        
        //When
        User userToReturn = userService.createUser(userEntity);

        //Then
        assertEquals(userToReturn, userEntity, "should return the same object");
    }
    
    @Test
    void should_update_user() {
       
    	//Given
        
        User userEntity = Data.createUser();
        
        userEntity.setUsername("oss");

        Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);
        
        Mockito.when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        
        //When
        User userToReturn = userService.updateUser(userEntity.getId(),userEntity);

        //Then
        assertThat(userToReturn).isNotNull();
    }
    
    
    @Test
    void should_getAll_tickets_by_user() {
       
    	//Given
        
        User userEntity = Data.createUser();
        Ticket ticketEntity = Data.createTicket();
        
        userEntity.setTickets(Arrays.asList(ticketEntity));
       
        
        Mockito.when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
                
        //When
        List<Ticket> listTicketsToReturn = userService.getAllTicketsByUserId(userEntity.getId())
        		.stream().collect(Collectors.toList());

        //Then
        assertEquals(ticketEntity, listTicketsToReturn.get(0));
    }
}

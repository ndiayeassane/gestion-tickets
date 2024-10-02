package net.ennov.gestion_ticket.service;

import java.util.Collection;

import net.ennov.gestion_ticket.entities.Ticket;
import net.ennov.gestion_ticket.entities.User;

public interface UserService {

	/**
	 * Service interface create User
	 * @param user
	 * @return User
	 */
	User createUser(User user);
	
	/**
	 * Service interface update User
	 * @param id 
	 * @param user
	 * @return User
	 */
	User updateUser(Long id,User user);
	
	/**
	 * Service interface get all users
	 * @return collection User
	 */
	Collection<User> getAllUsers();
	
	/**
	 * Service interface get all ticket by User id
	 * @param id 
	 * @return collection User
	 */
	Collection<Ticket> getAllTicketsByUserId(Long id);
}

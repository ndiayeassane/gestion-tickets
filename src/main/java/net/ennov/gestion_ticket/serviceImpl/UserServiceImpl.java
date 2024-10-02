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
import net.ennov.gestion_ticket.repository.UserRepository;
import net.ennov.gestion_ticket.service.UserService;
import net.ennov.gestion_ticket.utils.Constants;

/**
 * <h6> Service implement entity user</h6>
 * @author Assane Ndiaye
 *
 */

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private final UserRepository userRepository;
	
	/**
	 * Service Implement method to add new User
	 * @param uer
	 * @return User
	 */
	@Override
	public User createUser(User user) {
		
		log.info("Service Request to add new User {}",user);
		
		this.checkEmailValidation(user.getEmail());
		
		this.checkForUpdateUtilisateur(user);
		
		return userRepository.save(user);
	}


	/**
	 * Service Implement method to  update User
	 * @param uer
	 * @param id
	 * @return User
	 */
	@Override
	public User updateUser(Long id,User userBody) {
		
		log.info("Service Request to update User  id {}",id);
		
		Optional<User> optionalUser  = userRepository.findById(id);
		
		if(!optionalUser.isPresent()) throw new ApiRequestException(Constants.NO_DATA);
		
		return userRepository.save(userBody);
	}

	/**
	 * Service Implement method to  get all Users
	 * @return Collection User
	 */
	@Override
	public Collection<User> getAllUsers() {
		
		log.info("Service Request get all Users  ");
		
		return userRepository.findAll()
				.stream()
				.collect(Collectors.toList());
	}
	/**
	 * Service Implement method to  get all ticket by user
	 * @param id
	 * @return Collection Ticket
	 */
	@Override
	public Collection<Ticket> getAllTicketsByUserId(Long id) {
		
		log.info("Service Request get all tickets by user id {}  ",id);
		
		if(id == null) throw new ApiRequestException(Constants.NO_ID_FOUND);
		
		Optional<User> optUser = userRepository.findById(id);
		
		if(!optUser.isPresent()) throw new ApiRequestException(Constants.NO_DATA);
			
			return userRepository.findById(id).get().getTickets()
					.stream()
					.collect(Collectors.toList());
		
				
	}
	/**
	 * method to check if username or email user exists
	 * @param user
	 */
	 private void checkForUpdateUtilisateur(User user) {
	        Optional<User> utilisateurEmail = userRepository.findByEmail(user.getEmail());
	        Optional<User> usernameExists = userRepository.findByUsername(user.getUsername());
	        if(user.getId() != null)
	        {
	            if(utilisateurEmail.isPresent())
	                throw new ApiRequestException(Constants.EMAIL_EXISTS);
	            if(usernameExists.isPresent())
	                throw new ApiRequestException(Constants.USERNAME_EXISTS);

	        }
	    }

	    private void checkEmailValidation(String email){
	        if (email==null|| email=="" ||
	        		email.matches(Constants.EMAIL_PATTERN)) {
	            throw new ApiRequestException("No valid");

	        }
	    }

}

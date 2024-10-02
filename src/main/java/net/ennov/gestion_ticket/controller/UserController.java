package net.ennov.gestion_ticket.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.ennov.gestion_ticket.entities.Ticket;
import net.ennov.gestion_ticket.entities.User;
import net.ennov.gestion_ticket.service.UserService;

@RestController
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<Collection<User>> getAllUsers() {
		
		return ResponseEntity.ok().body(userService.getAllUsers());
	}
	
	@GetMapping("/users/{id}/ticket")
	public ResponseEntity<Collection<Ticket>> getUser(@PathVariable("id") Long id) {
		
		return ResponseEntity.ok().body(userService.getAllTicketsByUserId(id));
	}
	

	@PostMapping("/users")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		
		return ResponseEntity.ok().body(userService.createUser(user));
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		
		return ResponseEntity.ok().body(userService.updateUser(id, user));
	}
}

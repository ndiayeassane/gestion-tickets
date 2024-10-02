package net.ennov.gestion_ticket.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import net.ennov.gestion_ticket.entities.User;
import net.ennov.gestion_ticket.utils.Data;


@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {


	
	@Mock
	UserRepository userRepository;
	
	@Test
	void should_return_users() {
		
		 //Given
        
        User userEntity = Data.createUser();

        Mockito.when(userRepository.findAll()).thenReturn((Arrays.asList(userEntity)));

        //When
        List<User> users = userRepository.findAll();

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
        User userToReturn = userRepository.save(new User());

        //Then
        assertEquals(userToReturn, userEntity, "should return the same object");
    }
    
    @Test
    void should_get_user() {
       
    	//Given
        
        User userEntity1 = Data.createUser();
        
        Mockito.when(userRepository.findById(userEntity1.getId())).thenReturn(Optional.of(userEntity1));
        


        //When
    	User userEntity2 = userRepository.findById(userEntity1.getId()).orElseThrow(()->new IllegalStateException("Not found"));
    	
    	//then
    	assertEquals(userEntity1, userEntity2, "should return the same object");;
    }


}

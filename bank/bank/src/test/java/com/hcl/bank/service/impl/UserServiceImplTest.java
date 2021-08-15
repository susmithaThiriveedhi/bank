package com.hcl.bank.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.hcl.bank.dto.UserRequestDto;
import com.hcl.bank.entity.User;
import com.hcl.bank.exception.UserAlreadyExistsException;
import com.hcl.bank.repository.UserRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
    static UserRequestDto userRequestDto;
    static User user;
    
    @BeforeAll
	public void setUp() {
		userRequestDto = new UserRequestDto();
		userRequestDto.setUserName("suvidha24");
		userRequestDto.setFirstName("Suvidha");
		userRequestDto.setLastName("Thiriveedhi");
		userRequestDto.setEmailId("suvidha@gmail.com");
		userRequestDto.setGender("Female");
		userRequestDto.setAge(23);
		userRequestDto.setMobileNumber("9875676786");
		userRequestDto.setPassword("Suvidha@24");
		
		user=new User();
		user.setUserName("suvidha24");
		user.setFirstName("Suvidha");
		user.setLastName("Thiriveedhi");
		user.setEmailId("suvidha@gmail.com");
		user.setGender("Female");
		user.setAge(23);
		user.setMobileNumber("9875676786");
		user.setPassword("Suvidha@24");
	}
    
    @Test
	@DisplayName("Save User: Positive Scenario")
	public void saveUserTest() throws UserAlreadyExistsException {
		when(userRepository.save(any(User.class))).thenAnswer( i -> {
			User user = i.getArgument(0);
			user.setUserId(1L);
			user.setBalance(10000);
			Random rand = new Random();
			user.setAccountNumber("AcNo"+rand.nextInt(99999));
			return user;
		});
		
		String result = userServiceImpl.saveUser(userRequestDto);
		
		assertEquals("Account Created Successfully",result);
	}
    
//    @Test
//	@DisplayName("Save User: Negative Scenario")
//	public void saveUserTest1() throws UserAlreadyExistsException {
//		when(userRepository.save(any(User.class))).thenThrow(UserAlreadyExistsException.class);
//		assertThrows(UserAlreadyExistsException.class,()->userServiceImpl.saveUser(userRequestDto));
//	}
    @Test
   	@DisplayName("Save User: Negative Scenario")
   	public void saveUserTest1() throws UserAlreadyExistsException {
   		when(userRepository.save(any(User.class))).thenThrow(new UserAlreadyExistsException("User already exists...!!"));
   		userServiceImpl.saveUser(userRequestDto);
   		String result = userServiceImpl.saveUser(userRequestDto);
   		assertEquals("User already exists...!!",result);
   	}
    
    @Test
    @DisplayName("Get User: Positive Scenario")
    public void getUserTest() {
    	when(userRepository.findByAccountNumber(user.getAccountNumber())).thenReturn(user);
    	User user1=userServiceImpl.getUser(user.getAccountNumber());
    	verify(userRepository).findByAccountNumber(user.getAccountNumber());
    	assertEquals(user1,user);
    }
    
    @Test
    @DisplayName("Get User: Negative Scenario")
    public void getUserTest1() {
    	when(userRepository.findByAccountNumber(user.getAccountNumber())).thenReturn(null);
    	User user1=userServiceImpl.getUser(user.getAccountNumber());
    	assertNull(user1);
    } 
}

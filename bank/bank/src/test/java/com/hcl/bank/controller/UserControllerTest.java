package com.hcl.bank.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.hcl.bank.dto.UserRequestDto;
import com.hcl.bank.entity.User;
import com.hcl.bank.exception.UserAlreadyExistsException;
import com.hcl.bank.service.UserService;

//@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	@Mock
	UserService userService;

	@InjectMocks
	UserController userController;

	static UserRequestDto userRequestDto;
	
	static User user;

	@BeforeAll
	public static void setUp() {
		userRequestDto = new UserRequestDto();
		userRequestDto.setUserName("suvidha24");
		userRequestDto.setFirstName("Suvidha");
		userRequestDto.setLastName("Thiriveedhi");
		userRequestDto.setEmailId("suvidha@gmail.com");
		userRequestDto.setGender("Female");
		userRequestDto.setAge(23);
		userRequestDto.setMobileNumber("9875676786");
		userRequestDto.setPassword("Suvidha@24");
	}

	@Test
	@DisplayName("Save User: Positive Scenario")
	public void saveUserTest() throws UserAlreadyExistsException {
		when(userService.saveUser(userRequestDto)).thenReturn("Account Created Successfully");
		ResponseEntity<String> result = userController.saveUser(userRequestDto);
		verify(userService).saveUser(userRequestDto);
		assertEquals("Account Created Successfully", result.getBody());
	}
	
	@Test
	@DisplayName("Save User: Negative Scenario")
	public void saveUserTest1() throws UserAlreadyExistsException {
		when(userService.saveUser(userRequestDto)).thenReturn("User already exists...!!");
		ResponseEntity<String> result = userController.saveUser(userRequestDto);
		verify(userService).saveUser(userRequestDto);
		assertEquals("User already exists...!!",result.getBody());
	}

}

package com.hcl.bank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.bank.dto.UserRequestDto;
import com.hcl.bank.entity.User;
import com.hcl.bank.exception.UserAlreadyExistsException;
import com.hcl.bank.service.UserService;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping
	public ResponseEntity<String> saveUser(@Valid @RequestBody UserRequestDto userRequestDto) {
		ResponseEntity<String> responseEntity;
		try {
			String result = userService.saveUser(userRequestDto);
			//responseEntity = new ResponseEntity<>("Account \"" + user.getAccountNumber()+ "\" Created with an opening balance of \"" + user.getBalance() + "\"", HttpStatus.CREATED);
			responseEntity = new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (UserAlreadyExistsException e) {
			responseEntity = new ResponseEntity<>("User already exists...!!", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}

}

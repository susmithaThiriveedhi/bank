package com.hcl.bank.service.impl;

import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.bank.dto.UserRequestDto;
import com.hcl.bank.entity.User;
import com.hcl.bank.exception.UserAlreadyExistsException;
import com.hcl.bank.repository.UserRepository;
import com.hcl.bank.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public String saveUser(UserRequestDto userRequestDto) throws UserAlreadyExistsException{
		User user=new User();
		Random rand = new Random();
		BeanUtils.copyProperties(userRequestDto,user);
		user.setBalance(10000);
		user.setAccountNumber("AcNo"+rand.nextInt(99999));
		User userExisting = userRepository.findByMobileNumber(user.getMobileNumber());
		if (userExisting != null) {
			throw new UserAlreadyExistsException("User already exists...!!");		
		}
		userRepository.save(user);
		return "Account Created Successfully";
	}

	@Override
	public User getUser(String accountNumber) {
		return userRepository.findByAccountNumber(accountNumber);
	}

	@Override
	public User updateUser(String accountNumber,UserRequestDto userRequestDto) {
		User user=userRepository.findByAccountNumber(accountNumber);
		BeanUtils.copyProperties(userRequestDto,user);
		return userRepository.save(user);
	}
	
}

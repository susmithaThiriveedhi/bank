package com.hcl.bank.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.bank.dto.AmountTransactionRequestDto;
import com.hcl.bank.dto.AmountTransactionResponseDto;
import com.hcl.bank.dto.UserRequestDto;
import com.hcl.bank.entity.AmountTransaction;
import com.hcl.bank.entity.User;
import com.hcl.bank.repository.AmountTransactionRepository;
import com.hcl.bank.service.AmountTransactionService;
import com.hcl.bank.service.UserService;

@Service
public class AmountTransactionServiceImpl implements AmountTransactionService{
	
	@Autowired
	AmountTransactionRepository amountTransactionRepository;

	@Autowired
	UserService userService;
	
	@Override
	public String saveTransaction(AmountTransactionRequestDto amountTransactionRequestDto) {
		double amount=amountTransactionRequestDto.getAmount();
		String fromAccount=amountTransactionRequestDto.getFromAccountNumber();
		String toAccount=amountTransactionRequestDto.getToAccountNumber();
		if(fromAccount.equals(toAccount)) {
			return "Credit and debit account numbers both are same";
		}
		else {
		User fromUser=userService.getUser(fromAccount);
		UserRequestDto fromUserRequestDto=new UserRequestDto();
		BeanUtils.copyProperties(fromUser,fromUserRequestDto);
		if(fromUser.getBalance()>amount) {
		fromUser.setBalance(fromUser.getBalance()-amount);
		userService.updateUser(fromAccount,fromUserRequestDto);
		
		User toUser=userService.getUser(toAccount);
		UserRequestDto toUserRequestDto=new UserRequestDto();
		BeanUtils.copyProperties(toUser,toUserRequestDto);
		toUser.setBalance(toUser.getBalance()+amount);
		userService.updateUser(toAccount,toUserRequestDto);
		
		AmountTransaction amountTransaction=new AmountTransaction();
		BeanUtils.copyProperties(amountTransactionRequestDto,amountTransaction);
		amountTransaction.setTransactionDate(new Date());
		amountTransactionRepository.save(amountTransaction);
			return "Transactions done Successfully";
		}
		else {
			return "Insufficient Balance";
		}
		}
	}

	@Override
	public List<AmountTransactionResponseDto> getTransactions(String accountNumber, int month, int year) {
		List<AmountTransaction> amountTransaction=amountTransactionRepository.getTransactions(accountNumber,month,year);
		List<AmountTransactionResponseDto> amountTransactionResponseDtos=new ArrayList<>();		
		for(AmountTransaction transaction: amountTransaction) {
			AmountTransactionResponseDto amountTransactionResponseDto=new AmountTransactionResponseDto();
			BeanUtils.copyProperties(transaction,amountTransactionResponseDto);
			amountTransactionResponseDtos.add(amountTransactionResponseDto);
		}
		return amountTransactionResponseDtos;
	}
	
}

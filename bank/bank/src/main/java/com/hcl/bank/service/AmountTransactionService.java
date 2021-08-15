package com.hcl.bank.service;

import java.util.List;

import javax.validation.Valid;

import com.hcl.bank.dto.AmountTransactionRequestDto;
import com.hcl.bank.dto.AmountTransactionResponseDto;

public interface AmountTransactionService {

	String saveTransaction(@Valid AmountTransactionRequestDto amountTransactionRequestDto);

	List<AmountTransactionResponseDto> getTransactions(String accountNumber,int month,int year);
	
}

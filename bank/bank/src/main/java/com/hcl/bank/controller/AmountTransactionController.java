package com.hcl.bank.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.bank.dto.AmountTransactionRequestDto;
import com.hcl.bank.dto.AmountTransactionResponseDto;
import com.hcl.bank.service.AmountTransactionService;

@RestController
@RequestMapping("/transactions")
@Validated
public class AmountTransactionController {
	@Autowired
	AmountTransactionService amountTransactionService;
	
	@PostMapping
	public ResponseEntity<String> saveTransaction(@Valid @RequestBody AmountTransactionRequestDto amountTransactionRequestDto) {
		return new ResponseEntity<String>(amountTransactionService.saveTransaction(amountTransactionRequestDto), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<?> getTransactions(@RequestParam String accountNumber,@Valid @RequestParam @Min(1) @Max(12) int month,@Valid @RequestParam @Min(2021) @Max(3000) int year){
		List<AmountTransactionResponseDto> amountTransactionResponseDto=amountTransactionService.getTransactions(accountNumber,month,year);
		if(amountTransactionResponseDto.size()!=0) {
			return new ResponseEntity<List<AmountTransactionResponseDto>>(amountTransactionResponseDto,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("No Transactions Found",HttpStatus.NOT_FOUND);
		}
	}
}

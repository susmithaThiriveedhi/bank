package com.hcl.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.hcl.bank.entity.AmountTransaction;

public interface AmountTransactionRepository extends JpaRepository<AmountTransaction,Long>{

	@Query("from AmountTransaction where (fromAccountNumber=:accountNumber or toAccountNumber=:accountNumber) and month(transactionDate)=:month and Year(transactionDate)=:year")
	List<AmountTransaction> getTransactions(@Param("accountNumber") String accountNumber,@Param("month") int month,@Param("year") int year);

}

package com.hcl.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.bank.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	User findByAccountNumber(String accountNumber);

	User findByMobileNumber(String mobileNumber);

}

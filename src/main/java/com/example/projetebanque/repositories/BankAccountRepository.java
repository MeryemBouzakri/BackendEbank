package com.example.projetebanque.repositories;

import com.example.projetebanque.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository <BankAccount,String>{
}

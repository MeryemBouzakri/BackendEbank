package com.example.projetebanque.services;

import com.example.projetebanque.entities.BankAccount;
import com.example.projetebanque.entities.Costumer;
import com.example.projetebanque.entities.CurrentAccount;
import com.example.projetebanque.entities.SavingAccount;
import com.example.projetebanque.exceptions.BalanceNotSufficientExceptiom;
import com.example.projetebanque.exceptions.BankAccountNotFoundException;
import com.example.projetebanque.exceptions.CostumerNotFoundException;

import java.util.List;

public interface BankAccountService {
     Costumer saveCostumer(Costumer costumer);
     CurrentAccount saveCurrentBankAccount(double initialBalance , double overDraft, Long costumerId) throws CostumerNotFoundException;
     SavingAccount saveSavingBankAccount(double initialBalance , double interestRate, Long costumerId) throws CostumerNotFoundException;
     List<Costumer>listCostumer();
     BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
     void debit(String accountId, double amount ,String description) throws BankAccountNotFoundException, BalanceNotSufficientExceptiom;
      void credit(String accountId, double amount ,String description) throws BankAccountNotFoundException;
void tranfert (String accountIdSource ,String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientExceptiom;

     List<BankAccount>bankAccountList();
}

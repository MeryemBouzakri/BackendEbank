package com.example.projetebanque.services;

import com.example.projetebanque.dtos.*;
import com.example.projetebanque.entities.BankAccount;
import com.example.projetebanque.entities.Costumer;
import com.example.projetebanque.entities.CurrentAccount;
import com.example.projetebanque.entities.SavingAccount;
import com.example.projetebanque.exceptions.BalanceNotSufficientExceptiom;
import com.example.projetebanque.exceptions.BankAccountNotFoundException;
import com.example.projetebanque.exceptions.CostumerNotFoundException;

import java.util.List;

public interface BankAccountService {
     CostumerDTO saveCostumer(CostumerDTO costumerDTO);
     CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance , double overDraft, Long costumerId) throws CostumerNotFoundException;
     SavingBankAccountDTO saveSavingBankAccount(double initialBalance , double interestRate, Long costumerId) throws CostumerNotFoundException;
     List<CostumerDTO>listCostumer();
     BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
     void debit(String accountId, double amount ,String description) throws BankAccountNotFoundException, BalanceNotSufficientExceptiom;
      void credit(String accountId, double amount ,String description) throws BankAccountNotFoundException;
void tranfert (String accountIdSource ,String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientExceptiom;

     List<BankAccountDTO>bankAccountList();

     CostumerDTO getCostumer(Long costumerId) throws CostumerNotFoundException;

     CostumerDTO updateCostumer(CostumerDTO costumerDTO);

     void deleteCostumer(Long costumerID);

     List<AccountOperationDTO> accountHistory(String accountId);

     AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}

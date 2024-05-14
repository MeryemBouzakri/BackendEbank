package com.example.projetebanque.services;

import com.example.projetebanque.entities.*;
import com.example.projetebanque.enums.OperationType;
import com.example.projetebanque.exceptions.BalanceNotSufficientExceptiom;
import com.example.projetebanque.exceptions.BankAccountNotFoundException;
import com.example.projetebanque.exceptions.CostumerNotFoundException;
import com.example.projetebanque.repositories.AccountOperationRepository;
import com.example.projetebanque.repositories.BankAccountRepository;
import com.example.projetebanque.repositories.CostumerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j



public class BankAccountServiceImpl implements BankAccountService {
  private CostumerRepository costumerRepository;
  private BankAccountRepository bankAccountRepository;
  private AccountOperationRepository accountOperationRepository;

    @Override
    public Costumer saveCostumer(Costumer costumer) {
        log.info("Saving new costumer");
        Costumer savedCostumer = costumerRepository.save(costumer);
        return savedCostumer;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long costumerId) throws CostumerNotFoundException {
        Costumer costomer=costumerRepository.findById(costumerId).orElse(null);
        if(costomer==null)
            throw new CostumerNotFoundException("Costumer not found");
       CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId((UUID.randomUUID().toString()));
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCostumer(costomer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return savedBankAccount;

    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long costumerId) throws CostumerNotFoundException {
        Costumer costomer=costumerRepository.findById(costumerId).orElse(null);
        if(costomer==null)
            throw new CostumerNotFoundException("Costumer not found");
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId((UUID.randomUUID().toString()));
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCostumer(costomer);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return savedBankAccount;

    }


    @Override
    public List<Costumer> listCostumer() {
        return costumerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("BankAccount Not Found "));
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientExceptiom {
            BankAccount bankAccount=getBankAccount(accountId);
if (bankAccount.getBalance()<amount)
    throw new BalanceNotSufficientExceptiom("Balance Not Suffisient");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=getBankAccount(accountId);

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void tranfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientExceptiom {
        debit(accountIdSource,amount,"Transfer to"+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from"+accountIdSource);

    }
    @Override
    public List<BankAccount>bankAccountList(){
        return bankAccountRepository.findAll();
    }
}

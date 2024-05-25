package com.example.projetebanque.services;

import com.example.projetebanque.dtos.*;
import com.example.projetebanque.entities.*;
import com.example.projetebanque.enums.OperationType;
import com.example.projetebanque.exceptions.BalanceNotSufficientExceptiom;
import com.example.projetebanque.exceptions.BankAccountNotFoundException;
import com.example.projetebanque.exceptions.CostumerNotFoundException;
import com.example.projetebanque.mappers.BankAccountMappermpl;
import com.example.projetebanque.repositories.AccountOperationRepository;
import com.example.projetebanque.repositories.BankAccountRepository;
import com.example.projetebanque.repositories.CostumerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j



public class BankAccountServiceImpl implements BankAccountService {
  private CostumerRepository costumerRepository;
  private BankAccountRepository bankAccountRepository;
  private AccountOperationRepository accountOperationRepository;
private BankAccountMappermpl dtoMapper;
    @Override
    public CostumerDTO saveCostumer(CostumerDTO costumerDTO) {
        log.info("Saving new costumer");
        Costumer costumer = dtoMapper.fromCostumerDTO(costumerDTO);
        Costumer savedCostumer = costumerRepository.save(costumer);
        return dtoMapper.fromCostumer(savedCostumer);
    }
    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long costumerId) throws CostumerNotFoundException {
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
        return dtoMapper.fromCurrentBankAccount(savedBankAccount);

    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long costumerId) throws CostumerNotFoundException {
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
        return dtoMapper.fromSavingBankAccount(savedBankAccount);

    }


    @Override
    public List<CostumerDTO> listCostumer()
    {
        List<Costumer> costumers = costumerRepository.findAll();
        List<CostumerDTO> costumerDTOS = costumers.stream()
                .map(costumer -> dtoMapper.fromCostumer(costumer))
                .collect(Collectors.toList());
        /*
        List<CostumerDTO>costumerDTOS=new ArrayList<>();
        for (Costumer costumer:costumers)
        {CostumerDTO costumerDTO =dtoMapper.fromCostumer(costumer);
        costumerDTOS.add(costumerDTO);
        }*
         */
        return costumerDTOS;

    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("BankAccount Not Found "));
        if (bankAccount instanceof SavingAccount)
        {
            SavingAccount savingAccount=(SavingAccount) bankAccount;
        return dtoMapper.fromSavingBankAccount(savingAccount);
        }
        else {
            CurrentAccount currentAccount=(CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentAccount);
        }

    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientExceptiom {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("BankAccount Not Found "));if (bankAccount.getBalance()<amount)
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
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("BankAccount Not Found "));
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
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientExceptiom {
        debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+accountIdSource);
    }

    @Override
    public void tranfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientExceptiom {
        debit(accountIdSource,amount,"Transfer to"+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from"+accountIdSource);

    }
    @Override
    public List<BankAccountDTO>bankAccountList(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }
    @Override
    public CostumerDTO getCostumer(Long costumerId) throws CostumerNotFoundException {
        Costumer costumer = costumerRepository.findById(costumerId)
                .orElseThrow(() -> new CostumerNotFoundException("Custumer Not Found"));
        return dtoMapper.fromCostumer(costumer);

    }
    @Override
    public CostumerDTO updateCostumer(CostumerDTO costumerDTO) {
        log.info("Saving new costumer");
        Costumer costumer=dtoMapper.fromCostumerDTO(costumerDTO);
        Costumer savedCostumer = costumerRepository.save(costumer);
        return dtoMapper.fromCostumer(savedCostumer);
    }
    @Override
    public void deleteCostumer(Long costumerID){
        costumerRepository.deleteById(costumerID);
    }
    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
      return   accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount==null)throw new BankAccountNotFoundException("account not found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationsDTO = accountOperations.getContent().stream().map(op -> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationsDTO);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CostumerDTO> searchCustumers(String keyword) {
        List<Costumer>costumers=costumerRepository.searchCustomer(keyword);
        List<CostumerDTO> costumerDTOS = costumers.stream().map(cust -> dtoMapper.fromCostumer(cust)).collect(Collectors.toList());
   return costumerDTOS;
    }


}

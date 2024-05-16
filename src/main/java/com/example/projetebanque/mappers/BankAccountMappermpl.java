package com.example.projetebanque.mappers;

import com.example.projetebanque.dtos.AccountOperationDTO;
import com.example.projetebanque.dtos.CostumerDTO;
import com.example.projetebanque.dtos.CurrentBankAccountDTO;
import com.example.projetebanque.dtos.SavingBankAccountDTO;
import com.example.projetebanque.entities.AccountOperation;
import com.example.projetebanque.entities.Costumer;
import com.example.projetebanque.entities.CurrentAccount;
import com.example.projetebanque.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
//MapStruct: permet juste de creer l interface
@Service
public class BankAccountMappermpl {
    public CostumerDTO fromCostumer(Costumer costumer)
    {CostumerDTO costumerDTO=new CostumerDTO();
        BeanUtils.copyProperties(costumer,costumerDTO);
      //  costumerDTO.setId(costumer.getId());
        //costumerDTO.setName(costumer.getName());
        //costumerDTO.setMail(costumer.getMail());
        return costumerDTO;
    }
    public Costumer fromCostumerDTO(CostumerDTO costumerDTO)
    {Costumer costumer=new Costumer();
        BeanUtils.copyProperties(costumerDTO,costumer);
        return costumer;
    }
    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount)
    {
        SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
        savingBankAccountDTO.setCostumerDTO(fromCostumer(savingAccount.getCostumer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDTO;
    }
    public SavingAccount  fromSavingBankAccount(SavingBankAccountDTO savingBankAccountDTO)
    {
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setCostumer(fromCostumerDTO(savingBankAccountDTO.getCostumerDTO()));
        return savingAccount;
    }
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount)
    {
        CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCostumerDTO(fromCostumer(currentAccount.getCostumer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());

        return currentBankAccountDTO;
    }
    public CurrentAccount  fromSavingBankAccount(CurrentBankAccountDTO currentBankAccountDTO)
    {
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCostumer(fromCostumerDTO(currentBankAccountDTO.getCostumerDTO()));
        return currentAccount;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation)
    {
        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
   return accountOperationDTO;
    }


}

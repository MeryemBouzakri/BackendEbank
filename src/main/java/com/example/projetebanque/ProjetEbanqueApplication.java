package com.example.projetebanque;

import com.example.projetebanque.entities.*;
import com.example.projetebanque.enums.AccountStatus;
import com.example.projetebanque.enums.OperationType;
import com.example.projetebanque.exceptions.BalanceNotSufficientExceptiom;
import com.example.projetebanque.exceptions.BankAccountNotFoundException;
import com.example.projetebanque.exceptions.CostumerNotFoundException;
import com.example.projetebanque.repositories.AccountOperationRepository;
import com.example.projetebanque.repositories.BankAccountRepository;
import com.example.projetebanque.repositories.CostumerRepository;
import com.example.projetebanque.services.BankAccountService;
import com.example.projetebanque.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ProjetEbanqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetEbanqueApplication.class, args);
    }
@Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService ){
        return args -> {
            Stream.of("Meryem","Adnane","Mohamed").forEach(name->{
                Costumer costumer=new Costumer();
                costumer.setName(name);
                costumer.setMail(name+"gmail.com");
                bankAccountService.saveCostumer(costumer);
            });
            bankAccountService.listCostumer().forEach(costumer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000, costumer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*12000,5.5, costumer.getId());
                    List<BankAccount>bankAccounts=bankAccountService.bankAccountList();
                    for (BankAccount bankAccount:bankAccounts){
                        for (int i=0; i<10; i++){
                            bankAccountService.credit(bankAccount.getId(), 10000+Math.random()*120000,"Credit");
                            bankAccountService.debit(bankAccount.getId(), 1000+Math.random()*9000,"Debit");

                        }
                    }
                } catch (CostumerNotFoundException e) {
                    e.printStackTrace();
                } catch (BankAccountNotFoundException | BalanceNotSufficientExceptiom e) {
                    e.printStackTrace();
                }

            });

        };
}
  //@Bean
CommandLineRunner start(CostumerRepository costumerRepository,
                        BankAccountRepository bankAccountRepository,
                        AccountOperationRepository accountOperationRepository)
{return args -> {
    Stream.of("Meryem","Adnane","Yasmine").forEach(name->{
        Costumer costumer =new Costumer();
        costumer.setName(name);
        costumer.setMail(name+"@gmail.com");
        costumerRepository.save(costumer);
    });
    costumerRepository.findAll().forEach(cust->{
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(Math.random()*90000);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setCostumer(cust);
        currentAccount.setOverDraft(9000);
        bankAccountRepository.save(currentAccount);

        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(Math.random()*90000);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCostumer(cust);
        savingAccount.setInterestRate(5.5);
        bankAccountRepository.save(savingAccount);
    });
    bankAccountRepository.findAll().forEach(acc ->{
        for (int i=0 ;i<10;i++){
            AccountOperation accountOperation=new AccountOperation();
            accountOperation.setOperationDate(new Date());
            accountOperation.setAmount(Math.random()*12000);
            accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
            accountOperation.setBankAccount(acc);
            accountOperationRepository.save(accountOperation);

        }

    });

};

}
}

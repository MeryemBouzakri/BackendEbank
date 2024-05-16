package com.example.projetebanque.dtos;


import com.example.projetebanque.enums.AccountStatus;

import lombok.Data;


import java.util.Date;
import java.util.List;


@Data


public  class SavingBankAccountDTO extends BankAccountDTO{

    private String Id;
    private String rib;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CostumerDTO costumerDTO;
    private double interestRate;

}

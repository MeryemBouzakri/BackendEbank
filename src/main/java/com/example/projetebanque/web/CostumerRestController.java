package com.example.projetebanque.web;

import com.example.projetebanque.entities.Costumer;
import com.example.projetebanque.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CostumerRestController
{private BankAccountService bankAccountService;
    @GetMapping("customers")
public List<Costumer> costumers(){
    return bankAccountService.listCostumer();
}
}

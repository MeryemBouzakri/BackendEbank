package com.example.projetebanque.web;

import com.example.projetebanque.dtos.AccountOperationDTO;
import com.example.projetebanque.dtos.BankAccountDTO;
import com.example.projetebanque.dtos.CostumerDTO;
import com.example.projetebanque.entities.Costumer;
import com.example.projetebanque.exceptions.BankAccountNotFoundException;
import com.example.projetebanque.exceptions.CostumerNotFoundException;
import com.example.projetebanque.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CostumerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("customers")
    public List<CostumerDTO> costumers() {
        return bankAccountService.listCostumer();
    }

    @GetMapping("customers/{id}")
    public CostumerDTO getCostumer(@PathVariable(name = "id") Long costumerId) throws CostumerNotFoundException {
        return bankAccountService.getCostumer(costumerId);
    }

    @PostMapping("/customers")
    public CostumerDTO saveCostumer(@RequestBody CostumerDTO costumerDTO) {
        return bankAccountService.saveCostumer(costumerDTO);
    }

    @PutMapping("/customers/{costumerId}")
    public CostumerDTO updateCostumer(@PathVariable Long costumerId, @RequestBody CostumerDTO costumerDTO) {
        costumerDTO.setId(costumerId);
        return bankAccountService.updateCostumer(costumerDTO);
    }

    @DeleteMapping("customers/{id}")
    public void deleteCostumer(@PathVariable Long id) {
        bankAccountService.deleteCostumer(id);
    }
    @GetMapping("/customers/search")
    public List<CostumerDTO> searchCustomers(@RequestParam(name="keyword",defaultValue = "") String keyword)
    {
        return bankAccountService.searchCustumers("%"+keyword+"%");
    }

}

package com.example.projetebanque.mappers;

import com.example.projetebanque.dtos.CostumerDTO;
import com.example.projetebanque.entities.Costumer;
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
    {
        return null;
    }

}

package com.example.projetebanque.dtos;

import com.example.projetebanque.entities.BankAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
public class CostumerDTO {

    private Long id ;
    private String name;
    private String mail;


}

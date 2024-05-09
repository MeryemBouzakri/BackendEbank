package com.example.projetebanque.repositories;

import com.example.projetebanque.entities.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumerRepository extends JpaRepository <Costumer,Long>{
}

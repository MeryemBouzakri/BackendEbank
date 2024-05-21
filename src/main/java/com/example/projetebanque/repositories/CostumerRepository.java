package com.example.projetebanque.repositories;

import com.example.projetebanque.entities.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CostumerRepository extends JpaRepository <Costumer,Long>{
   @Query("select c from Costumer c where c.name like :kw")
    List<Costumer>searchCustomer(@Param("kw") String keyword);

}

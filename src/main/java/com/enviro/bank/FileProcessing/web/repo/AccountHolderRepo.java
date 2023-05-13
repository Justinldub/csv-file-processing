package com.example.demo.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.web.model.AccountHolder;

@Repository
public interface AccountHolderRepo extends JpaRepository<AccountHolder,Integer>{

}

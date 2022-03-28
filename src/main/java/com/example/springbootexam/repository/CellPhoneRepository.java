package com.example.springbootexam.repository;

import com.example.springbootexam.entity.CellPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellPhoneRepository extends JpaRepository<CellPhone, Integer> {
}

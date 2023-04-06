package com.kawe.belajarrest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kawe.belajarrest.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
}

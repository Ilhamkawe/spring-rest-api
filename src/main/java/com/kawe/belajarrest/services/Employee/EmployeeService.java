package com.kawe.belajarrest.services.Employee;

import java.util.List;

import com.kawe.belajarrest.entity.Employee;

interface EmployeeService {
    
    List<Employee> findAll();

    Employee findByID(int id);

    Employee save(Employee employee);

    boolean delete(Employee employee);

}

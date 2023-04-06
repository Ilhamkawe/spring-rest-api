package com.kawe.belajarrest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kawe.belajarrest.dao.EmployeeRepository;
import com.kawe.belajarrest.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeDAO; 

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employee) {
        this.employeeDAO = employee;
    }

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Override
    public Employee findByID(int id) {
        Optional<Employee> res = employeeDAO.findById(id);
        
        Employee emp = null;

        if (res.isPresent()){
            emp = res.get();
        }else { 
            throw new RuntimeException("Data tidak ditemukan");
        }

        return emp;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeDAO.save(employee);
    }
    
    @Override
    public boolean delete(Employee employee) {
        employeeDAO.delete(employee);

        Optional<Employee> res = employeeDAO.findById(employee.getId());
        
        Employee emp = null;

        if (res.isPresent()){
            emp = res.get();
        }else { 
            throw new RuntimeException("Data tidak ditemukan");
        }

        if (emp == null) {
            return true;
        }
        
        return false;
    }
    
}

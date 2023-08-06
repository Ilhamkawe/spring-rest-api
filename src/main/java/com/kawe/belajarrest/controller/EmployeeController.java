package com.kawe.belajarrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kawe.belajarrest.entity.Employee;
import com.kawe.belajarrest.services.Employee.EmployeeServiceImpl;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EmployeeController {
    
    private EmployeeServiceImpl employee;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employee) {
        this.employee = employee;
    }

    @GetMapping("/employees")
    public List<Employee> findAll(){
        return employee.findAll();

    }

    @GetMapping("/employee/{employeeID}")
    public Employee FinByID(@PathVariable int employeeID){
        
        Employee theEmployee = employee.findByID(employeeID);

        if (theEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeID);
        }

        return theEmployee;

    }

    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee theEmployee){
        Employee newEmployee = employee.save(theEmployee);

        return newEmployee;

    }
    @PutMapping("/employee")
    public Employee editEmployee(@RequestBody Employee theEmployee){
        Employee newEmployee = employee.save(theEmployee);

        return newEmployee;

    }

    @DeleteMapping("/employee/{employeeID}")
    public String deleteEmployee(@PathVariable int employeeID){
        
        Employee theEmployee = employee.findByID(employeeID); 

        if (theEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeID);
        }


        boolean isDeleted = employee.delete(theEmployee);
        
        if (isDeleted) {
            return "Berhasil hapus data employee id - " + employeeID; 
        }


        return "Berhasil hapus data employee id - " + employeeID;
    }


}

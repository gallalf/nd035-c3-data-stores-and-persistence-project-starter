package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.*;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable){

        employee.setSkills(skills);
        employee.setDaysAvailable(daysAvailable);
        employee = employeeRepository.save(employee);

        return employee;
    }

    public Employee getEmployee(long employeeId){
        Optional<Employee> item = employeeRepository.findById(employeeId);
        if(item.isPresent()){
            return item.get();
        }
        else{
            throw new EmployeeNotFoundException("Employee not found");
        }
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Optional<Employee> item = employeeRepository.findById(employeeId);
        if(item.isPresent()){
            Employee employee = item.get();
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        }
        else{
            throw new EmployeeNotFoundException("Employee not found");
        }
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek) {

        List<Employee> employeeList = employeeRepository.findDistinctBySkillsInAndDaysAvailableContains(skills, dayOfWeek);
        return employeeList;
    }
}

package com.udacity.jdnd.course3.critter.schedule.service;

import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.pet.service.PetNotFoundException;
import com.udacity.jdnd.course3.critter.schedule.model.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.service.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.service.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public void createSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds){

        if(employeeIds != null) {
            Set<Employee> employeeIdList = Sets.newHashSet();
            employeeIds.forEach(employeeId ->{
                Optional<Employee> employeeItem = employeeRepository.findById(employeeId);
                if(employeeItem.isPresent()){
                    employeeIdList.add(employeeItem.get());
                }
                else{
                    throw new EmployeeNotFoundException("Employee not found");
                }
            });
            schedule.setEmployees(employeeIdList);
        }
        if(petIds != null){
            Set<Pet> petIdList = Sets.newHashSet();
            petIds.forEach(petId ->{
                Optional<Pet> petItem = petRepository.findById(petId);
                if(petItem.isPresent()){
                    petIdList.add(petItem.get());
                }
                else{
                    throw new PetNotFoundException("Pet not found");
                }
            });
            schedule.setPets(petIdList);
        }

        scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
       return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId){

        Optional<Pet> petItem = petRepository.findById(petId);
        if(petItem.isPresent()){
            return scheduleRepository.findByPets(petItem.get());
        }
        else{
            throw new PetNotFoundException("Pet not found");
        }
    }

    public List<Schedule> getScheduleForEmployee(long employeeId){

        Optional<Employee> employeeItem = employeeRepository.findById(employeeId);
        if(employeeItem.isPresent()) {
            return scheduleRepository.findAllByEmployeesContains(employeeItem.get());
        }
        else{
            throw new EmployeeNotFoundException("Employee not found");
        }
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {

        Optional<Customer> customerItem = customerRepository.findById(customerId);
        if(customerItem.isPresent()) {
            List<Pet> petList = petRepository.findByOwner(customerItem.get());
            return scheduleRepository.findAllByPetsIn(Sets.newHashSet(petList));
        }
        else{
            throw new CustomerNotFoundException("Customer not found");
        }
    }
}

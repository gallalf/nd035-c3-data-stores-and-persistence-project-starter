package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.pet.service.PetNotFoundException;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public Customer getCustomer(Long customerId){
        if(customerId != null){
            Optional<Customer> customerItem = customerRepository.findById(customerId);
            if(customerItem.isPresent()){
                return customerItem.get();
            }
        }
        return new Customer();
    }

    public void setPetList(List<Long> petIds, Customer customer){
        List<Pet> pets = new ArrayList<>();
        if(petIds != null){
            petIds.forEach(pet ->{
                Optional<Pet> petItem = petRepository.findById(pet.longValue());
                if(petItem.isPresent()){
                    pets.add(petItem.get());
                }
                else{
                    throw new PetNotFoundException("Pet not found");
                }
            });
        }
        customer.setPetIds(pets);
    }

    public void savePetList(Long customerId, List<Long> petList){
        Optional<Customer> customerItem = customerRepository.findById(customerId);
        if(customerItem.isPresent()){
            Customer customer = customerItem.get();
            setPetList(petList, customer);
            customerRepository.save(customer);
        }
        else{
            throw new CustomerNotFoundException("Customer not found");
        }
    }

    public Customer saveCustomer(Customer customer, List<Long> petList){

        setPetList(petList, customer);
        customer = customerRepository.save(customer);
        return customer;
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(long petId){

        Optional<Pet> item = petRepository.findById(petId);
        if(item.isPresent()){
            return item.get().getOwnerId();
        }
        else{
            throw new PetNotFoundException("Pet not found");
        }
    }
}

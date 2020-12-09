package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.service.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    public Pet savePet(Pet pet, long ownerId) {

        Optional<Customer> customerItem = customerRepository.findById(ownerId);
        if(customerItem.isPresent()){
            pet.setOwnerId(customerItem.get());
        }
        else{
            throw new CustomerNotFoundException("Customer not found");
        }

        pet.setType(pet.getType());
        pet = petRepository.save(pet);
        customerService.savePetList(customerItem.get().getId(), Arrays.asList(pet.getId()));

        return pet;
    }

    public Pet getPet(long petId){

        Optional<Pet> petItem = petRepository.findById(petId);
        if(petItem.isPresent()){
            return petItem.get();
        }
        else{
            throw new PetNotFoundException("Pet not found");
        }
    }

    public List<Pet> getPets(){
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId){

        Optional<Customer> customerItem = customerRepository.findById(ownerId);
        if(customerItem.isPresent()){
            return petRepository.findByOwnerId(customerItem.get());
        }
        else{
            throw new CustomerNotFoundException("Customer not found");
        }
    }
}

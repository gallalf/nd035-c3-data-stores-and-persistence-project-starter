package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.Utils;
import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.model.PetType;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.pet.service.PetNotFoundException;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
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

    private Customer getCustomer(Long customerId){
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

    public CustomerDTO saveCustomer(CustomerDTO customerDTO){

        Customer customer = getCustomer(customerDTO.getId());
        BeanUtils.copyProperties(customerDTO, customer);
        setPetList(customerDTO.getPetIds(), customer);
        customer = customerRepository.save(customer);
        customerDTO.setId(customer.getId());
        return customerDTO;
    }

    public List<CustomerDTO> getAllCustomers(){

        List<CustomerDTO> customerDTOList = new ArrayList<>();
        List<Customer> customerList = customerRepository.findAll();

        customerList.forEach(customer -> {
            CustomerDTO customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(customer, customerDTO);
            if(customer.getPetIds() != null) {
                List<Long> petIds = new ArrayList<>();
                customer.getPetIds().forEach(pet -> {
                    petIds.add(pet.getId());
                });
                customerDTO.setPetIds(petIds);
            }
            customerDTOList.add(customerDTO);
        });

        return customerDTOList;
    }

    public CustomerDTO getOwnerByPet(long petId){

        Optional<Pet> item = petRepository.findById(petId);
        if(item.isPresent()){
            Customer customer = item.get().getOwnerId();
            CustomerDTO customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(customer, customerDTO);
            if(customer.getPetIds() != null) {
                List<Long> petIds = new ArrayList<>();
                customer.getPetIds().forEach(pet -> {
                    petIds.add(pet.getId());
                });
                customerDTO.setPetIds(petIds);
            }
            return customerDTO;
        }
        else{
            throw new PetNotFoundException("Pet not found");
        }

    }
}

package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.Utils;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.pet.repository.PetTypeRepository;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.service.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    PetTypeRepository petTypeRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    public PetDTO savePet(PetDTO petDTO) {

        Pet pet = new Pet();
        PetDTO newPetDTO = new PetDTO();
        BeanUtils.copyProperties(petDTO, pet);
        BeanUtils.copyProperties(petDTO, newPetDTO);

        Optional<Customer> customerItem = customerRepository.findById(petDTO.getOwnerId());
        if(customerItem.isPresent()){
            pet.setOwnerId(customerItem.get());
        }
        else{
            throw new CustomerNotFoundException("Customer not found");
        }

        pet.setType(petTypeRepository.findPetTypeByName(Utils.getPetType(petDTO.getType())));
        pet = petRepository.save(pet);
        customerService.savePetList(customerItem.get().getId(), Arrays.asList(pet.getId()));
        newPetDTO.setId(pet.getId());
        return newPetDTO;
    }

    public PetDTO getPet(long petId){

        Optional<Pet> petItem = petRepository.findById(petId);
        if(petItem.isPresent()){
            Pet pet = petItem.get();
            PetDTO petDTO = Utils.createPetDTO(pet);
            return petDTO;
        }
        else{
            throw new PetNotFoundException("Pet not found");
        }
    }

    public List<PetDTO> getPets(){

        List<PetDTO> petDTOList = new ArrayList<>();
        List<Pet> petList = petRepository.findAll();
        petList.forEach(pet -> {
            PetDTO petDTO = Utils.createPetDTO(pet);
            petDTOList.add(petDTO);
        });

        return petDTOList;
    }

    public List<PetDTO> getPetsByOwner(long ownerId){

        List<PetDTO> petDTOList = new ArrayList<>();
        List<Pet> petList = petRepository.findPetByOwnerId(ownerId);
        petList.forEach(pet->{
            PetDTO petDTO = Utils.createPetDTO(pet);
            petDTOList.add(petDTO);
        });

        return petDTOList;
    }
}

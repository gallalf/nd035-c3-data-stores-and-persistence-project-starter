package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertToPet(petDTO);
        return convertToPetDTO(petService.savePet(pet, petDTO.getOwnerId()));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertToPetDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return convertToPetListDTO(petService.getPets());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return convertToPetListDTO(petService.getPetsByOwner(ownerId));
    }

    /** CONVERSION METHODS **/

    private Pet convertToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    private PetDTO convertToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());

        return petDTO;
    }

    private List<PetDTO> convertToPetListDTO(List<Pet> petList){
        List<PetDTO> petDTOList = Lists.newArrayList();
        petList.forEach(pet->{
            PetDTO petDTO = convertToPetDTO(pet);
            petDTOList.add(petDTO);
        });
        return petDTOList;
    }
}

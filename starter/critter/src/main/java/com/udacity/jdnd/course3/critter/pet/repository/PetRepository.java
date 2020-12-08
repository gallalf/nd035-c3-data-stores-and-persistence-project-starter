package com.udacity.jdnd.course3.critter.pet.repository;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("select p from com.udacity.jdnd.course3.critter.pet.model.Pet p where p.ownerId.id = :ownerId")
    List<Pet> findPetByOwnerId(Long ownerId);

}

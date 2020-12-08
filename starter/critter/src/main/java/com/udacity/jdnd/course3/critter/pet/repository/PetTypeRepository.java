package com.udacity.jdnd.course3.critter.pet.repository;

import com.udacity.jdnd.course3.critter.pet.model.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Long> {

    @Query("select p from com.udacity.jdnd.course3.critter.pet.model.PetType p where p.type = :petType")
    PetType findPetTypeByName(String petType);
}

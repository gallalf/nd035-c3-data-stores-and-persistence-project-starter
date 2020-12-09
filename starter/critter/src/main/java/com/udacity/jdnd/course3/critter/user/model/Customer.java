package com.udacity.jdnd.course3.critter.user.model;

import com.udacity.jdnd.course3.critter.pet.model.Pet;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer extends Person  {

    private String phoneNumber;
    private String notes;

    @JoinColumn(referencedColumnName = "id")
    @ManyToMany
    private List<Pet> pets;

    public Customer() {
    }

    public Customer(String phoneNumber, String notes, List<Pet> pets) {
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.pets = pets;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

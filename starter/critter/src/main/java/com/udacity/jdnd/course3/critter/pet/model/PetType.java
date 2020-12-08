package com.udacity.jdnd.course3.critter.pet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity(name="pet_type")
public class PetType {
    @Id
    @GeneratedValue
    private Long id;

    private String type;

    public PetType() {
    }

    public PetType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetType petType = (PetType) o;
        return Objects.equals(id, petType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

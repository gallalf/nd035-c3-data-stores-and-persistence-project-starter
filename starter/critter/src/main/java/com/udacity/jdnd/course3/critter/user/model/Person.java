package com.udacity.jdnd.course3.critter.user.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * I changed the strategy, because I think for this kind of application,
 * it gives a better performance
 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public class Person {

    @Id
    @GeneratedValue
    protected Long id;

    protected String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

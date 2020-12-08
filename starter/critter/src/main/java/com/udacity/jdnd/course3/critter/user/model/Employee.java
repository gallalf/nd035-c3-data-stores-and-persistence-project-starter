package com.udacity.jdnd.course3.critter.user.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;


@Entity
public class Employee extends Person {

    @JoinColumn(referencedColumnName = "id")
    @OneToMany
    private Set<EmployeeSkill> skills;

    @JoinColumn(referencedColumnName = "id")
    @OneToMany
    private List<DayOfWeek> daysAvailable;

    public Employee() {
    }

    public Employee(String name, Set<EmployeeSkill> skills, List<DayOfWeek> daysAvailable) {
        this.skills = skills;
        this.daysAvailable = daysAvailable;
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public List<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(List<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}

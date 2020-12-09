package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Set;

/**
 * Utilitary methods
 */
public class Utils {

    public static List<Long> getEmployeeIdSet(Set<Employee> employeeList){
        List<Long> employeeIdList = Lists.newArrayList();
        employeeList.forEach(employee -> {
            employeeIdList.add(employee.getId());
        });
        return employeeIdList;
    }

    public static List<Long> getPetIdList(Set<Pet> petList){
        List<Long> petIdList = Lists.newArrayList();
        petList.forEach(pet -> {
            petIdList.add(pet.getId());
        });
        return petIdList;
    }
}

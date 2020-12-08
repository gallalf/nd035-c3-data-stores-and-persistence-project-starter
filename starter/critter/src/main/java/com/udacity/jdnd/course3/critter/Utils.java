package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.model.Schedule;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import org.assertj.core.util.Sets;
import org.springframework.beans.BeanUtils;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {

    public static List<String> getEmployeeSkillList(Set<EmployeeSkill> skills){
        List<String> employeeSkillList = new ArrayList<>();
        if(skills != null)
            skills.forEach(skill -> employeeSkillList.add(skill.name()));
        return employeeSkillList;
    }

    public static List<String> getDayOfWeekList(Set<DayOfWeek> dayOfWeeks){
        List<String> dayOfWeekList = new ArrayList<>();
        if(dayOfWeeks != null)
            dayOfWeeks.forEach(dayOfWeek -> dayOfWeekList.add(dayOfWeek.name()));
        return dayOfWeekList;
    }

    public static Set<EmployeeSkill> getEmployeeSkillSet(List<com.udacity.jdnd.course3.critter.user.model.EmployeeSkill> skills){

        if(skills != null && !skills.isEmpty()) {
            Set<EmployeeSkill> employeeSkillSet = new HashSet<>();
            skills.forEach(skill -> employeeSkillSet.add(EmployeeSkill.valueOf(skill.getSkill())));
            return employeeSkillSet;
        }
        return null;
    }

    public static Set<DayOfWeek> getDayOfWeekSet(List<com.udacity.jdnd.course3.critter.user.model.DayOfWeek> dayOfWeeks){

        if(dayOfWeeks != null && !dayOfWeeks.isEmpty()){
            Set<DayOfWeek> dayOfWeekSet = new HashSet<>();
            dayOfWeeks.forEach(dayOfWeek -> dayOfWeekSet.add(DayOfWeek.valueOf(dayOfWeek.getDayOfWeek())));
            return dayOfWeekSet;
        }
        return null;
    }

    public static PetType getPetType(com.udacity.jdnd.course3.critter.pet.model.PetType petType){
        return PetType.valueOf(petType.getType());
    }

    public static String getPetType(PetType petType){
        return petType.name();
    }

    public static PetDTO createPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setType(Utils.getPetType(pet.getType()));
        petDTO.setOwnerId(pet.getOwnerId().getId());

        return petDTO;
    }

    public static List<Long> getEmployeeIdList(List<Employee> employeeList){
        List<Long> employeeIdList = new ArrayList<>();
        employeeList.forEach(employee -> {
            employeeIdList.add(employee.getId());
        });
        return employeeIdList;
    }

    public static List<Long> getPetIdList(List<Pet> petList){
        List<Long> petIdList = new ArrayList<>();
        petList.forEach(pet -> {
            petIdList.add(pet.getId());
        });
        return petIdList;
    }

    public static ScheduleDTO createScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(getEmployeeSkillSet(schedule.getActivities()));
        scheduleDTO.setEmployeeIds(getEmployeeIdList(schedule.getEmployeeIds()));
        scheduleDTO.setPetIds(getPetIdList(schedule.getPetIds()));

        return scheduleDTO;
    }
}

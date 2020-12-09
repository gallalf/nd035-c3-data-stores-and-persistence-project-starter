package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.model.Schedule;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Set;

public class Utils {

    public static PetDTO createPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwnerId().getId());

        return petDTO;
    }

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

    public static ScheduleDTO createScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setEmployeeIds(getEmployeeIdSet(schedule.getEmployeeIds()));
        scheduleDTO.setPetIds(getPetIdList(schedule.getPetIds()));

        return scheduleDTO;
    }

    public static List<ScheduleDTO> convertScheduleList(List<Schedule> scheduleList){
        List<ScheduleDTO> scheduleDTOList = Lists.newArrayList();
        scheduleList.forEach(schedule -> {
            ScheduleDTO scheduleDTO = Utils.createScheduleDTO(schedule);
            scheduleDTOList.add(scheduleDTO);
        });
        return scheduleDTOList;
    }

    public static List<PetDTO> convertPetList(List<Pet> petList){
        List<PetDTO> petDTOList = Lists.newArrayList();
        petList.forEach(pet->{
            PetDTO petDTO = Utils.createPetDTO(pet);
            petDTOList.add(petDTO);
        });
        return petDTOList;
    }
}

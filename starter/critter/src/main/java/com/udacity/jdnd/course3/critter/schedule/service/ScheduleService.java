package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.Utils;
import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.pet.service.PetNotFoundException;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.model.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.model.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeSkillRepository;
import com.udacity.jdnd.course3.critter.user.service.EmployeeNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO){

        Schedule schedule = new Schedule();
        ScheduleDTO newScheduleDTO = new ScheduleDTO();

        if(scheduleDTO.getEmployeeIds() != null) {
            List<Employee> employeeIdList = new ArrayList<>();
            scheduleDTO.getEmployeeIds().forEach(employeeId ->{
                Optional<Employee> employeeItem = employeeRepository.findById(employeeId);
                if(employeeItem.isPresent()){
                    employeeIdList.add(employeeItem.get());
                }
                else{
                    throw new EmployeeNotFoundException("Employee not found");
                }
            });
            schedule.setEmployeeIds(employeeIdList);
        }
        if(scheduleDTO.getPetIds() != null){
            List<Pet> petIdList = new ArrayList<>();
            scheduleDTO.getPetIds().forEach(petId ->{
                Optional<Pet> petItem = petRepository.findById(petId);
                if(petItem.isPresent()){
                    petIdList.add(petItem.get());
                }
                else{
                    throw new PetNotFoundException("Pet not found");
                }
            });
            schedule.setPetIds(petIdList);
        }

        schedule.setDate(scheduleDTO.getDate());

        List<String> skillNameList = Utils.getEmployeeSkillList(scheduleDTO.getActivities());
        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findSkillsByName(skillNameList);

        schedule.setActivities(employeeSkillList);
        schedule = scheduleRepository.save(schedule);

        BeanUtils.copyProperties(scheduleDTO, newScheduleDTO);
        return newScheduleDTO;
    }

    public List<ScheduleDTO> getAllSchedules(){

        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        List<Schedule> scheduleList = scheduleRepository.findAll();
        scheduleList.forEach(schedule -> {
            ScheduleDTO scheduleDTO = Utils.createScheduleDTO(schedule);
            scheduleDTOList.add(scheduleDTO);
        });

        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleForPet(long petId){
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        Optional<Pet> petItem = petRepository.findById(petId);
        if(petItem.isPresent()){
            Pet pet = petItem.get();
            List<Schedule> scheduleList = scheduleRepository.findByPetIds(pet);
            scheduleList.forEach(schedule -> {
                ScheduleDTO scheduleDTO = Utils.createScheduleDTO(schedule);
                scheduleDTOList.add(scheduleDTO);
            });
        }
        else{
            throw new PetNotFoundException("Pet not found");
        }

        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleForEmployee(long employeeId){
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        Optional<Employee> employeeItem = employeeRepository.findById(employeeId);
        if(employeeItem.isPresent()){

        }
        else{
            throw new EmployeeNotFoundException("Employee not found");
        }

        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        List<Pet> petList = petRepository.findPetByOwnerId(customerId);



        return scheduleDTOList;
    }
}

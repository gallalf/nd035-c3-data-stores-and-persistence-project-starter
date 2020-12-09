package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.schedule.model.Schedule;
import com.udacity.jdnd.course3.critter.schedule.service.ScheduleService;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.udacity.jdnd.course3.critter.Utils.getEmployeeIdSet;
import static com.udacity.jdnd.course3.critter.Utils.getPetIdList;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertToSchedule(scheduleDTO);
        scheduleService.createSchedule(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return convertToScheduleListDTO(scheduleService.getAllSchedules());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return convertToScheduleListDTO(scheduleService.getScheduleForPet(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return convertToScheduleListDTO(scheduleService.getScheduleForEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return convertToScheduleListDTO(scheduleService.getScheduleForCustomer(customerId));
    }

    /** CONVERSION METHODS **/

    private List<ScheduleDTO> convertToScheduleListDTO(List<Schedule> scheduleList){
        List<ScheduleDTO> scheduleDTOList = Lists.newArrayList();
        scheduleList.forEach(schedule -> {
            ScheduleDTO scheduleDTO = convertToScheduleDTO(schedule);
            scheduleDTOList.add(scheduleDTO);
        });
        return scheduleDTOList;
    }

    private Schedule convertToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }

    private ScheduleDTO convertToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setEmployeeIds(getEmployeeIdSet(schedule.getEmployees()));
        scheduleDTO.setPetIds(getPetIdList(schedule.getPets()));

        return scheduleDTO;
    }
}

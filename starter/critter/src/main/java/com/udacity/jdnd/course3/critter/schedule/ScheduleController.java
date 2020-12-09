package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.Utils;
import com.udacity.jdnd.course3.critter.schedule.model.Schedule;
import com.udacity.jdnd.course3.critter.schedule.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Schedule schedule = new Schedule();
        ScheduleDTO newScheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        scheduleService.createSchedule(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
        BeanUtils.copyProperties(scheduleDTO, newScheduleDTO);
        return newScheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return Utils.convertScheduleList(scheduleService.getAllSchedules());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return Utils.convertScheduleList(scheduleService.getScheduleForPet(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return Utils.convertScheduleList(scheduleService.getScheduleForEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return Utils.convertScheduleList(scheduleService.getScheduleForCustomer(customerId));
    }
}

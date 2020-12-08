package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.Utils;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.model.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.repository.DayOfWeekRepository;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeSkillRepository;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    @Autowired
    private DayOfWeekRepository dayOfWeekRepository;

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO){

        Employee employee = new Employee();
        EmployeeDTO newEmployeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employeeDTO, employee);
        BeanUtils.copyProperties(employeeDTO, newEmployeeDTO);

        employee.setSkills(Sets.newHashSet(employeeSkillRepository.findSkillsByName(Utils.getEmployeeSkillList(employeeDTO.getSkills()))));
        employee.setDaysAvailable(dayOfWeekRepository.findDaysOfWeekByName(Utils.getDayOfWeekList(employeeDTO.getDaysAvailable())));

        employee = employeeRepository.save(employee);
        newEmployeeDTO.setId(employee.getId());

        return newEmployeeDTO;
    }

    public EmployeeDTO getEmployee(long employeeId){
        Optional<Employee> item = employeeRepository.findById(employeeId);
        if(item.isPresent()){
            EmployeeDTO employeeDTO = new EmployeeDTO();
            Employee employee = item.get();
            BeanUtils.copyProperties(employee, employeeDTO);
            employeeDTO.setSkills(Utils.getEmployeeSkillSet(Lists.newArrayList(employee.getSkills())));
            employeeDTO.setDaysAvailable(Utils.getDayOfWeekSet(employee.getDaysAvailable()));

            return employeeDTO;
        }
        else{
            throw new EmployeeNotFoundException("Employee not found");
        }
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Optional<Employee> item = employeeRepository.findById(employeeId);
        if(item.isPresent()){
            Employee employee = item.get();
            employee.setDaysAvailable(dayOfWeekRepository.findDaysOfWeekByName(Utils.getDayOfWeekList(daysAvailable)));
            employeeRepository.save(employee);
        }
        else{
            throw new EmployeeNotFoundException("Employee not found");
        }
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        com.udacity.jdnd.course3.critter.user.model.DayOfWeek dayOfWeek = dayOfWeekRepository.findDaysOfWeekByName(Arrays.asList(employeeDTO.getDate().getDayOfWeek().name())).get(0);
        List<EmployeeSkill> employeeSkills = employeeSkillRepository.findSkillsByName(Utils.getEmployeeSkillList(employeeDTO.getSkills()));

        List<Employee> employeeList = employeeRepository.findAllBySkillsInAndDaysAvailableContains(Sets.newHashSet(employeeSkills), dayOfWeek);
        employeeList.forEach(employee -> {
            if(employee.getSkills().containsAll(employeeSkills)) {
                EmployeeDTO employeeResponseDTO = new EmployeeDTO();
                BeanUtils.copyProperties(employee, employeeResponseDTO);
                employeeResponseDTO.setSkills(Utils.getEmployeeSkillSet(Lists.newArrayList(employee.getSkills())));
                employeeResponseDTO.setDaysAvailable(Utils.getDayOfWeekSet(employee.getDaysAvailable()));
                employeeDTOList.add(employeeResponseDTO);
            }
        });

        return employeeDTOList;

    }
}

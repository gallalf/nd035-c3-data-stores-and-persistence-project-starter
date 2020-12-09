package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerService.getCustomer(customerDTO.getId());
        CustomerDTO newCustomerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customerDTO, customer);
        customer = customerService.saveCustomer(customer, customerDTO.getPetIds());
        BeanUtils.copyProperties(customer, newCustomerDTO);
        return newCustomerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOList = Lists.newArrayList();
        List<Customer> customerList = customerService.getAllCustomers();
        customerList.forEach(customer -> {
            CustomerDTO customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(customer, customerDTO);
            if(customer.getPetIds() != null) {
                List<Long> petIds = new ArrayList<>();
                customer.getPetIds().forEach(pet -> {
                    petIds.add(pet.getId());
                });
                customerDTO.setPetIds(petIds);
            }
            customerDTOList.add(customerDTO);
        });
        return customerDTOList;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        CustomerDTO customerDTO = new CustomerDTO();
        Customer customer = customerService.getOwnerByPet(petId);
        BeanUtils.copyProperties(customer, customerDTO);
        if(customer.getPetIds() != null) {
            List<Long> petIds = new ArrayList<>();
            customer.getPetIds().forEach(pet -> {
                petIds.add(pet.getId());
            });
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        EmployeeDTO newEmployeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee = employeeService.saveEmployee(employee, employeeDTO.getSkills(), employeeDTO.getDaysAvailable());
        BeanUtils.copyProperties(employeeDTO, newEmployeeDTO);
        newEmployeeDTO.setId(employee.getId());

        return newEmployeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable,employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<EmployeeDTO> employeeDTOList = Lists.newArrayList();

        List<Employee> employeeList = employeeService.findEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());
        employeeList.forEach(employee -> {
            if(employee.getSkills().containsAll(employeeDTO.getSkills())) {
                EmployeeDTO employeeResponseDTO = new EmployeeDTO();
                BeanUtils.copyProperties(employee, employeeResponseDTO);
                employeeResponseDTO.setSkills(employee.getSkills());
                employeeResponseDTO.setDaysAvailable(employee.getDaysAvailable());
                employeeDTOList.add(employeeResponseDTO);
            }
        });
        return employeeDTOList;
    }

}

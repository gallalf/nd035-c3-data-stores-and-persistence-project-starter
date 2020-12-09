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
        BeanUtils.copyProperties(customerDTO, customer);
        customer = customerService.saveCustomer(customer, customerDTO.getPetIds());
        return convertToCustomerDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return convertToCustomerListDTO(customerService.getAllCustomers());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return convertToCustomerDTO(customerService.getOwnerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertToEmployee(employeeDTO);
        return convertToEmployeeDTO(employeeService.saveEmployee(employee, employeeDTO.getSkills(), employeeDTO.getDaysAvailable()));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return convertToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable,employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return convertToEmployeeListDTO(employeeService.findEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek()), employeeDTO.getSkills());
    }

    /** CONVERSION METHODS **/

    private CustomerDTO convertToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if(customer.getPets() != null) {
            List<Long> petIds = new ArrayList<>();
            customer.getPets().forEach(pet -> {
                petIds.add(pet.getId());
            });
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    private List<CustomerDTO> convertToCustomerListDTO(List<Customer> customerList){
        List<CustomerDTO> customerDTOList = Lists.newArrayList();
        customerList.forEach(customer -> {
            CustomerDTO customerDTO = convertToCustomerDTO(customer);
            customerDTOList.add(customerDTO);
        });
        return customerDTOList;
    }

    private Employee convertToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private EmployeeDTO convertToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }

    private  List<EmployeeDTO> convertToEmployeeListDTO(List<Employee> employeeList, Set<EmployeeSkill> skills){
        List<EmployeeDTO> employeeDTOList = Lists.newArrayList();
        employeeList.forEach(employee -> {
            if(employee.getSkills().containsAll(skills)) {
                EmployeeDTO employeeResponseDTO = convertToEmployeeDTO(employee);
                employeeDTOList.add(employeeResponseDTO);
            }
        });
        return employeeDTOList;
    }
}

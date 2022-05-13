package org.asoiu.QueueManagementSystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.LoginEmployeeDto;
import org.asoiu.QueueManagementSystem.dto.RegisterEmployeeDto;
import org.asoiu.QueueManagementSystem.entity.Employee;
import org.asoiu.QueueManagementSystem.exception.MyExceptionClass;
import org.asoiu.QueueManagementSystem.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Log4j2
@Service
@RequiredArgsConstructor
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    public Employee register(RegisterEmployeeDto registerEmployeeDto) throws MyExceptionClass {
        log.info("STARTED: " + " register ");
        log.info("REGISTER EMPLOYEE DTO: " + registerEmployeeDto);
        Optional<Employee> opEmployee = employeeRepository.findEmployeeByEmail(registerEmployeeDto.getEmail());
        if (opEmployee.isPresent())
            throw new MyExceptionClass("Employee already exists with email: " + registerEmployeeDto.getEmail());
        ModelMapper mapper = new ModelMapper();
        Employee employee = mapper.map(registerEmployeeDto, Employee.class);

        log.info("EMPLOYEE: " + employee);
        log.info("FINISHED: " + " register ");
        return employeeRepository.save(employee);
    }

    public Employee login(LoginEmployeeDto loginEmployeeDto) throws MyExceptionClass {
        log.info("STARTED: " + " login ");
        log.info("LOGIN EMPLOYEE DTO: " + loginEmployeeDto);
        Employee employee = employeeRepository.findEmployeeByEmail(loginEmployeeDto.getEmail())
                .orElseThrow(() -> new MyExceptionClass("Employee not found with ID: " + loginEmployeeDto.getEmail()));
        if (!employee.getPassword().equals(loginEmployeeDto.getPassword()))
            throw new RuntimeException("Password doesn't match.");

        log.info("EMPLOYEE: " + employee);
        log.info("FINISHED: " + " login ");
        return employee;
    }
}

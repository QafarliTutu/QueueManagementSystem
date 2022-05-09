package org.asoiu.QueueManagementSystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.LoginEmployeeDto;
import org.asoiu.QueueManagementSystem.dto.RegisterEmployeeDto;
import org.asoiu.QueueManagementSystem.entity.Employee;
import org.asoiu.QueueManagementSystem.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    public Employee register(RegisterEmployeeDto registerEmployeeDto) {
        Optional<Employee> opEmployee = employeeRepository.findEmployeeByEmail(registerEmployeeDto.getEmail());
        if (opEmployee.isPresent())
            throw new RuntimeException("employee already exists with email" + registerEmployeeDto.getEmail());
        ModelMapper mapper = new ModelMapper();
        Employee employee = mapper.map(registerEmployeeDto, Employee.class);
        return employeeRepository.save(employee);
    }

    public Employee login(LoginEmployeeDto loginEmployeeDto) {
        Employee employee = employeeRepository.findEmployeeByEmail(loginEmployeeDto.getEmail())
                .orElseThrow(() -> new RuntimeException("employee not found with id: " + loginEmployeeDto.getEmail()));
        if (!employee.getPassword().equals(loginEmployeeDto.getPassword()))
            throw new RuntimeException("password doesn't match");
        return employee;
    }
}

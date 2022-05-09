package org.asoiu.QueueManagementSystem.controller;

import lombok.RequiredArgsConstructor;
import org.asoiu.QueueManagementSystem.dto.LoginEmployeeDto;
import org.asoiu.QueueManagementSystem.dto.RegisterEmployeeDto;
import org.asoiu.QueueManagementSystem.dto.ServiceResponse;
import org.asoiu.QueueManagementSystem.entity.Employee;
import org.asoiu.QueueManagementSystem.service.EmployeeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/register")
    public ServiceResponse<Employee> register(@RequestBody RegisterEmployeeDto registerEmployeeDto) {
        Employee employee = employeeService.register(registerEmployeeDto);
        return ServiceResponse.<Employee>builder()
                .successful(true)
                .payload(employee)
                .build();
    }

    @PostMapping("/login")
    public ServiceResponse<Employee> login(@RequestBody LoginEmployeeDto loginEmployeeDto) {
        Employee employee = employeeService.login(loginEmployeeDto);
        return ServiceResponse.<Employee>builder()
                .successful(true)
                .payload(employee)
                .build();
    }
}

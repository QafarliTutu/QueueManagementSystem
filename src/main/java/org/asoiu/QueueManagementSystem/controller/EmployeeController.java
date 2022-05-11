package org.asoiu.QueueManagementSystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.LoginEmployeeDto;
import org.asoiu.QueueManagementSystem.dto.RegisterEmployeeDto;
import org.asoiu.QueueManagementSystem.dto.ServiceResponse;
import org.asoiu.QueueManagementSystem.entity.Employee;
import org.asoiu.QueueManagementSystem.exception.MyExceptionClass;
import org.asoiu.QueueManagementSystem.service.EmployeeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/register")
    public ServiceResponse<Employee> register(@RequestBody RegisterEmployeeDto registerEmployeeDto) throws MyExceptionClass {
        log.info("CALLED: " + " register " + "REQUEST BODY= " + registerEmployeeDto.toString());
        Employee employee = employeeService.register(registerEmployeeDto);
        return ServiceResponse.<Employee>builder()
                .successful(true)
                .payload(employee)
                .build();
    }

    @PostMapping("/login")
    public ServiceResponse<Employee> login(@RequestBody LoginEmployeeDto loginEmployeeDto) {
        log.info("CALLED: " + " login " + "REQUEST BODY= " + loginEmployeeDto.toString());
        Employee employee = employeeService.login(loginEmployeeDto);
        return ServiceResponse.<Employee>builder()
                .successful(true)
                .payload(employee)
                .build();
    }


}

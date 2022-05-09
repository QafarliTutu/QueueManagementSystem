package org.asoiu.QueueManagementSystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.LoginStudentDto;
import org.asoiu.QueueManagementSystem.dto.RegisterStudentDto;
import org.asoiu.QueueManagementSystem.dto.ServiceResponse;
import org.asoiu.QueueManagementSystem.entity.Student;
import org.asoiu.QueueManagementSystem.service.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register")
    public ServiceResponse<Student> register(@RequestBody RegisterStudentDto registerStudentDto) {
        Student student = studentService.register(registerStudentDto);
        return ServiceResponse.<Student>builder()
                .successful(true)
                .payload(student)
                .build();
    }

    @PostMapping("/login")
    public ServiceResponse<Student> login(@RequestBody LoginStudentDto loginStudentDto) {
        Student student = studentService.login(loginStudentDto);
        return ServiceResponse.<Student>builder()
                .successful(true)
                .payload(student)
                .build();
    }
}

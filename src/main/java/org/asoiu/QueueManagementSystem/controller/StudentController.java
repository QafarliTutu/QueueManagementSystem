package org.asoiu.QueueManagementSystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.LoginStudentDto;
import org.asoiu.QueueManagementSystem.dto.MySchedulesResponse;
import org.asoiu.QueueManagementSystem.dto.RegisterStudentDto;
import org.asoiu.QueueManagementSystem.dto.ServiceResponse;
import org.asoiu.QueueManagementSystem.entity.Student;
import org.asoiu.QueueManagementSystem.exception.MyExceptionClass;
import org.asoiu.QueueManagementSystem.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register")
    public ServiceResponse<Student> register(@RequestBody RegisterStudentDto registerStudentDto) throws MyExceptionClass {
        log.info("CALLED: " + " register " + "REQUEST BODY= " + registerStudentDto.toString());
        Student student = studentService.register(registerStudentDto);
        return ServiceResponse.<Student>builder()
                .successful(true)
                .payload(student)
                .build();
    }

    @PostMapping("/login")
    public ServiceResponse<Student> login(@RequestBody LoginStudentDto loginStudentDto) throws MyExceptionClass {
        log.info("CALLED: " + " login " + "REQUEST BODY= " + loginStudentDto.toString());
        Student student = studentService.login(loginStudentDto);
        return ServiceResponse.<Student>builder()
                .successful(true)
                .payload(student)
                .build();
    }

    @GetMapping("/myscs/{studentId}")
    public ServiceResponse<List<MySchedulesResponse>> searchMySchedules(@PathVariable Long studentId) throws MyExceptionClass {
        log.info("CALLED: " + " searchMySchedules " + "PATH VARIABLE: " + studentId);
        List<MySchedulesResponse> mySchedulesResponses = studentService.searchMySchedules(studentId);
        return ServiceResponse.<List<MySchedulesResponse>>builder()
                .successful(true)
                .payload(mySchedulesResponses)
                .build();
    }

}

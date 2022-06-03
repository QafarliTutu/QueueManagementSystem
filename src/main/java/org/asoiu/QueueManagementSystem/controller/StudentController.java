package org.asoiu.QueueManagementSystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.LoginStudentDto;
import org.asoiu.QueueManagementSystem.dto.MySchedulesResponse;
import org.asoiu.QueueManagementSystem.dto.RegisterStudentDto;
import org.asoiu.QueueManagementSystem.dto.ServiceResponse;
import org.asoiu.QueueManagementSystem.entity.Student;
import org.asoiu.QueueManagementSystem.exception.MyExceptionClass;
import org.asoiu.QueueManagementSystem.repository.StudentRepository;
import org.asoiu.QueueManagementSystem.service.StudentService;
import org.asoiu.QueueManagementSystem.util.SearchCriteria;
import org.asoiu.QueueManagementSystem.util.StudentSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository repository;

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

    @GetMapping("/search")
    public List<Student> search(){
        StudentSpecifications spec1 =
                new StudentSpecifications(new SearchCriteria("name", ":", ""));
        StudentSpecifications spec2 =
                new StudentSpecifications(new SearchCriteria("pinCode", ":", ""));

        List<Student> results = repository.findAll(Specification.where(spec1).and(spec2));
        System.out.println(results);
        return results;
    }

}

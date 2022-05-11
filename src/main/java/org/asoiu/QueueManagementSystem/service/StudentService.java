package org.asoiu.QueueManagementSystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.LoginStudentDto;
import org.asoiu.QueueManagementSystem.dto.RegisterStudentDto;
import org.asoiu.QueueManagementSystem.entity.Student;
import org.asoiu.QueueManagementSystem.repository.StudentRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public Student register(RegisterStudentDto registerStudentDto) {
        log.info("STARTED: " + " register ");
        log.info("REGISTER STUDENT DTO: " + registerStudentDto);
        Optional<Student> opStudent = studentRepository.findStudentByEmail(registerStudentDto.getEmail());
        if (opStudent.isPresent())
            throw new RuntimeException("Student already exists with email" + registerStudentDto.getEmail());
        ModelMapper mapper = new ModelMapper();
        Student student = mapper.map(registerStudentDto, Student.class);
        log.info("STUDENT: " + student);
        log.info("FINISHED: " + " register ");
        return studentRepository.save(student);
    }

    public Student login(LoginStudentDto loginStudentDto) {
        log.info("STARTED: " + " login ");
        log.info("LOGIN STUDENT DTO: " + loginStudentDto);
        Student student = studentRepository.findStudentByEmail(loginStudentDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + loginStudentDto.getEmail()));
        if (!student.getPassword().equals(loginStudentDto.getPassword()))
            throw new RuntimeException("Password doesn't match");
        log.info("STUDENT: " + student);
        log.info("FINISHED: " + " login ");
        return student;
    }


}

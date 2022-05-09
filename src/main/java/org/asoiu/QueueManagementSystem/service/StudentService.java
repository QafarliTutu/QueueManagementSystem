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
        Optional<Student> opStudent = studentRepository.findStudentByEmail(registerStudentDto.getEmail());
        if (opStudent.isPresent())
            throw new RuntimeException("student already exists with email" + registerStudentDto.getEmail());
        ModelMapper mapper = new ModelMapper();
        Student student = mapper.map(registerStudentDto, Student.class);
        return studentRepository.save(student);
    }

    public Student login(LoginStudentDto loginStudentDto) {
        Student student = studentRepository.findStudentByEmail(loginStudentDto.getEmail())
                .orElseThrow(() -> new RuntimeException("student not found with id: " + loginStudentDto.getEmail()));
        if (!student.getPassword().equals(loginStudentDto.getPassword()))
            throw new RuntimeException("password doesn't match");
        return student;
    }
}

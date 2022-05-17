package org.asoiu.QueueManagementSystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.LoginStudentDto;
import org.asoiu.QueueManagementSystem.dto.MySchedulesResponse;
import org.asoiu.QueueManagementSystem.dto.RegisterStudentDto;
import org.asoiu.QueueManagementSystem.entity.Schedule;
import org.asoiu.QueueManagementSystem.entity.Student;
import org.asoiu.QueueManagementSystem.exception.MyExceptionClass;
import org.asoiu.QueueManagementSystem.repository.ScheduleRepository;
import org.asoiu.QueueManagementSystem.repository.StudentRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final ScheduleRepository scheduleRepository;


    public Student register(RegisterStudentDto registerStudentDto) throws MyExceptionClass {
        log.info("STARTED: " + " register ");
        log.info("REGISTER STUDENT DTO: " + registerStudentDto);
        Optional<Student> opStudent = studentRepository.findStudentByEmail(registerStudentDto.getEmail());
        if (opStudent.isPresent())
            throw new MyExceptionClass("Student already exists with email: " + registerStudentDto.getEmail());
        ModelMapper mapper = new ModelMapper();
        Student student = mapper.map(registerStudentDto, Student.class);
        log.info("STUDENT: " + student);
        log.info("FINISHED: " + " register ");
        return studentRepository.save(student);
    }

    public Student login(LoginStudentDto loginStudentDto) throws MyExceptionClass {
        log.info("STARTED: " + " login ");
        log.info("LOGIN STUDENT DTO: " + loginStudentDto);
        Student student = studentRepository.findStudentByEmail(loginStudentDto.getEmail())
                .orElseThrow(() -> new MyExceptionClass("Student not found with email: " + loginStudentDto.getEmail()));
        if (!student.getPassword().equals(loginStudentDto.getPassword()))
            throw new RuntimeException("Password doesn't match.");
        log.info("STUDENT: " + student);
        log.info("FINISHED: " + " login ");
        return student;
    }

    public List<MySchedulesResponse> searchMySchedules(Long studentId) throws MyExceptionClass {
        log.info("STARTED: " + " searchMySchedules ");
        log.info("STUDENT ID: " + studentId);
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new MyExceptionClass("Student not found with ID: " + studentId));
        List<MySchedulesResponse> responseList = scheduleRepository.findAllByStudent(student).stream()
                .map(schedule -> {
                    MySchedulesResponse scResponse = new MySchedulesResponse();
                    scResponse.setId(schedule.getScheduleId());
                    scResponse.setEventId(schedule.getEvent().getEventId());
                    scResponse.setStudentId(studentId);
                    scResponse.setAvailableDate(schedule.getAvailableDate().toString());
                    return scResponse;
                }).collect(Collectors.toList());

        log.info("MySchedulesResponse: " + responseList);
        log.info("FINISHED: " + " searchMySchedules ");
        return responseList;
    }


}

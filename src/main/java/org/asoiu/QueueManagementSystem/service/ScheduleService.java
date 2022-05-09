package org.asoiu.QueueManagementSystem.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.entity.Event;
import org.asoiu.QueueManagementSystem.entity.Schedule;
import org.asoiu.QueueManagementSystem.entity.Student;
import org.asoiu.QueueManagementSystem.repository.EventRepository;
import org.asoiu.QueueManagementSystem.repository.ScheduleRepository;
import org.asoiu.QueueManagementSystem.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepo;
    private final StudentRepository studentRepo;
    private final EventRepository eventRepo;

    public List<Schedule> createSchedule(Event event){
        log.info("STARTED: " + " createSchedule ");
        log.info("EVENT: " + event);
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(event.getStartDate());
        calendar.add(Calendar.HOUR_OF_DAY, 9);
        calendar2.setTime(event.getStartDate());
        calendar2.add(Calendar.HOUR_OF_DAY, 18);
        List<Schedule> scheduleList = new ArrayList<>();

        for (int i = 0; ; i++) {
            Schedule schedule = new Schedule();
            schedule.setEvent(event);
            schedule.setIsAvailable(true);
            calendar.add(Calendar.MINUTE, i * 15);
            schedule.setAvailableDate(calendar.getTime());
            scheduleList.add(schedule);
            if (calendar.getTime().getTime() > calendar2.getTime().getTime()) break;
        }
        log.info("SCHEDULE LIST: " + scheduleList);
        log.info("FINISHED: " + " createSchedule ");
        return scheduleRepo.saveAll(scheduleList);

    }

    public List<Schedule> getAllSchedule(Long eventId){
        log.info("STARTED: " + " getAllSchedule ");
        log.info("EVENTID: " + eventId);

        Event event = eventRepo.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));
        List<Schedule> scheduleList = scheduleRepo.findAllByEvent(event);
        log.info("SCHEDULE LIST: " + scheduleList);
        log.info("FINISHED: " + " getAllSchedule ");
        return scheduleList;
    }

    public Schedule makeReserve(Long studentId, Long scheduleId){
        Student student = studentRepo.findById(studentId).orElseThrow(()-> new RuntimeException("Student not found with ID: " + studentId));
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow(()-> new RuntimeException("Schedule not found with ID: " + scheduleId));
        schedule.setStudent(student);
        schedule.setIsAvailable(false);
        scheduleRepo.save(schedule);
        return schedule;
    }

    public Schedule cancelSchedule(Long scheduleId){
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow(()-> new RuntimeException("Schedule not found with ID: " + scheduleId));
        schedule.setIsAvailable(true);
        schedule.setStudent(null);
        return schedule;
    }



}

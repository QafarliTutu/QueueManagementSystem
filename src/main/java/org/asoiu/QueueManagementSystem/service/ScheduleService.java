package org.asoiu.QueueManagementSystem.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.entity.Event;
import org.asoiu.QueueManagementSystem.entity.Schedule;
import org.asoiu.QueueManagementSystem.entity.Student;
import org.asoiu.QueueManagementSystem.repository.EventRepository;
import org.asoiu.QueueManagementSystem.repository.ScheduleRepository;
import org.asoiu.QueueManagementSystem.repository.StudentRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepo;
    private final StudentRepository studentRepo;
    private final EventRepository eventRepo;
    private final EmailSenderService emailSenderService;

    public List<Schedule> createSchedule(Event event){
        log.info("STARTED: " + " createSchedule ");
        log.info("EVENT: " + event);
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(event.getStartDate());
        calendar.add(Calendar.HOUR_OF_DAY, 9);
        calendar2.setTime(event.getStartDate());
        calendar2.add(Calendar.HOUR_OF_DAY, 17);
        calendar2.add(Calendar.MINUTE, 45);
        List<Schedule> scheduleList = new ArrayList<>();

        for (int i = 0; ; i++) {
            Schedule schedule = new Schedule();
            schedule.setEvent(event);
            schedule.setIsAvailable(true);
            schedule.setIsCompleted(0);
            calendar.add(Calendar.MINUTE, 15);
            schedule.setAvailableDate(LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()));
            scheduleList.add(schedule);
            if (calendar.getTime().getTime() >= calendar2.getTime().getTime()) break;
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
        log.info("STARTED: " + " makeReserve ");
        log.info("STUDENTID: " + studentId + " SCHEDULEID: " + scheduleId);
        Student student = studentRepo.findById(studentId).orElseThrow(()-> new RuntimeException("Student not found with ID: " + studentId));
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow(()-> new RuntimeException("Schedule not found with ID: " + scheduleId));
        schedule.setStudent(student);
        schedule.setIsAvailable(false);
        student.setSchedule(schedule);
        scheduleRepo.save(schedule);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(student.getEmail());
        mail.setSubject("Rezervasiya məlumatlarının təqdim olunması.");
        mail.setFrom("myfirstcalculatorapp@gmail.com");
        mail.setText("Rezervasiya prosesi uğurla icra olundu. Sizin qeydiyyat üçün yaxınlaşma vaxtınız: " + schedule.getAvailableDate());
//        emailSenderService.sendEmail(mail);

        log.info("STUDENT: " + student);
        log.info("SCHEDULE: " + schedule);
        log.info("MAIL SENT: " + mail);
        log.info("FINISHED: " + " makeReserve ");
        return schedule;
    }

    public Schedule cancelSchedule(Long scheduleId){
        log.info("STARTED: " + " cancelSchedule ");
        log.info(" SCHEDULEID: " + scheduleId);
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow(()-> new RuntimeException("Schedule not found with ID: " + scheduleId));
        schedule.setIsAvailable(true);
        schedule.setStudent(null);
        log.info("SCHEDULE: " + schedule);
        log.info("FINISHED: " + " cancelSchedule ");
        return schedule;
    }


    public PriorityQueue<Schedule> getQueue(Long eventId) {
        List<Schedule> schedules = getAllSchedule(eventId)
                .stream()
                .filter(schedule -> !schedule.getIsAvailable())
                .filter(schedule -> schedule.getIsCompleted()==0)
                .collect(Collectors.toList());

        PriorityQueue<Schedule> queue = new PriorityQueue<>(schedules);
        return queue;
    }

    public Schedule completeReservation(Long scheduleId){
        Optional<Schedule> byId = scheduleRepo.findById(scheduleId);
        byId.ifPresent(schedule -> {
            schedule.setIsCompleted(1);
            scheduleRepo.save(schedule);
        });

        return getQueue(byId.get().getEvent().getEventId()).peek();
    }

    public Schedule declineReservation(Long scheduleId){
        Optional<Schedule> byId = scheduleRepo.findById(scheduleId);
        byId.ifPresent(schedule -> {
            schedule.setIsCompleted(-1);
            scheduleRepo.save(schedule);
        });
        return getQueue(byId.get().getEvent().getEventId()).peek();
    }

}

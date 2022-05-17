package org.asoiu.QueueManagementSystem.service;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.ReserveDto;
import org.asoiu.QueueManagementSystem.dto.ScheduleDto;
import org.asoiu.QueueManagementSystem.entity.Event;
import org.asoiu.QueueManagementSystem.entity.Schedule;
import org.asoiu.QueueManagementSystem.entity.Student;
import org.asoiu.QueueManagementSystem.exception.MyExceptionClass;
import org.asoiu.QueueManagementSystem.repository.EventRepository;
import org.asoiu.QueueManagementSystem.repository.ScheduleRepository;
import org.asoiu.QueueManagementSystem.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
        calendar.setTime(java.util.Date.from(event.getStartDate().atZone(ZoneId.systemDefault()).toInstant()));
     //   calendar.add(Calendar.HOUR_OF_DAY, 9);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(java.util.Date.from(event.getEndDate().atZone(ZoneId.systemDefault()).toInstant()));
       // calendar2.add(Calendar.HOUR_OF_DAY, 17);
       // calendar2.add(Calendar.MINUTE, 45);

        List<Schedule> scheduleList = new ArrayList<>();

        for (int i = 0; ; i++) {
            if (calendar.getTime().getTime() >= calendar2.getTime().getTime()) break;
            if (isWorkHour(calendar.get(Calendar.HOUR_OF_DAY))) {
                Schedule schedule = new Schedule();
                schedule.setEvent(event);
                schedule.setIsAvailable(true);
                schedule.setIsCompleted(0);
                schedule.setAvailableDate(LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()));
                scheduleList.add(schedule);
            }
            calendar.add(Calendar.MINUTE, 15);
        }
        log.info("SCHEDULE LIST: " + scheduleList);
        log.info("FINISHED: " + " createSchedule ");
        return scheduleRepo.saveAll(scheduleList);

    }


    private boolean isWorkHour(int hour) {
        return 9 <= hour && hour < 18;
    }

    public Map<LocalDate, List<ScheduleDto>> getAllSchedule(Long eventId) throws MyExceptionClass {
        log.info("STARTED: " + " getAllSchedule ");
        log.info("EVENT ID: " + eventId);
        Event event = eventRepo.findById(eventId).orElseThrow(() -> new MyExceptionClass("Event not found with ID: " + eventId));
        List<Schedule> scheduleList = scheduleRepo.findAllByEvent(event);
        ModelMapper mapper = new ModelMapper();
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();

        scheduleList.stream().forEach(schedule -> {
            ScheduleDto scheduleDto = new ScheduleDto();
            mapper.map(schedule,scheduleDto);
            scheduleDto.setAvailableDay(scheduleDto.getAvailableDate().toLocalDate());
            scheduleDtoList.add(scheduleDto);
        });
        log.info("SCHEDULE DTO LIST: " + scheduleDtoList);
        log.info("FINISHED: " + " getAllSchedule ");
        return scheduleDtoList.stream()
                .sorted()
                .collect(Collectors.groupingBy(ScheduleDto::getAvailableDay));
    }

    public Schedule makeReserve(Long studentId, Long scheduleId) throws MyExceptionClass {
        log.info("STARTED: " + " makeReserve ");
        log.info("STUDENT ID: " + studentId + " SCHEDULE ID: " + scheduleId);
        Student student = studentRepo.findById(studentId).orElseThrow(()-> new MyExceptionClass("Student not found with ID: " + studentId));
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow(()-> new MyExceptionClass("Schedule not found with ID: " + scheduleId));
        schedule.setStudent(student);
        schedule.setIsAvailable(false);
        List<Schedule> schedules = student.getSchedule();
        schedules.add(schedule);
        student.setSchedule(schedules);
        scheduleRepo.save(schedule);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(student.getEmail());
        mail.setSubject("Rezervasiya məlumatlarının təqdim olunması.");
        mail.setFrom("myfirstcalculatorapp@gmail.com");
        mail.setText("Rezervasiya prosesi uğurla icra olundu. Sizin qeydiyyat üçün yaxınlaşma vaxtınız: " + schedule.getAvailableDate());
        emailSenderService.sendEmail(mail);

        log.info("STUDENT: " + student);
        log.info("SCHEDULE: " + schedule);
        log.info("MAIL SENT: " + mail);
        log.info("FINISHED: " + " makeReserve ");
        return schedule;
    }

    public Schedule cancelSchedule(Long scheduleId) throws MyExceptionClass {
        log.info("STARTED: " + " cancelSchedule ");
        log.info("SCHEDULE ID: " + scheduleId);
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow(()-> new MyExceptionClass("Schedule not found with ID: " + scheduleId));
        schedule.setIsAvailable(true);
        schedule.setStudent(null);
        scheduleRepo.save(schedule);
        log.info("SCHEDULE: " + schedule);
        log.info("FINISHED: " + " cancelSchedule ");
        return schedule;
    }


    public PriorityQueue<Schedule> getQueue(Long eventId) throws MyExceptionClass {
        log.info("STARTED: " + " getQueue ");
        log.info("EVENT ID: " + eventId);
        Event event = eventRepo.findById(eventId).orElseThrow(() -> new MyExceptionClass("Event not found with ID: " + eventId));
        List<Schedule> schedules = scheduleRepo.findAllByEvent(event)
                .stream()
                .filter(schedule -> !schedule.getIsAvailable())
                .filter(schedule -> schedule.getIsCompleted() == 0)
                .collect(Collectors.toList());
        PriorityQueue<Schedule> queue = new PriorityQueue<>(schedules);
        log.info("SCHEDULES: " + schedules);
        log.info("FINISHED: " + " getQueue ");
        return queue;
    }

    public Schedule completeReservation(Long scheduleId) throws MyExceptionClass {
        log.info("STARTED: " + " completeReservation ");
        log.info("SCHEDULE ID: " + scheduleId);
        Optional<Schedule> byId = scheduleRepo.findById(scheduleId);
        byId.ifPresent(schedule -> {
            schedule.setIsCompleted(1);
            scheduleRepo.save(schedule);
        });
        log.info("SCHEDULE: " + byId);
        log.info("FINISHED: " + " completeReservation ");
        return getQueue(byId.get().getEvent().getEventId()).peek();
    }

    public Schedule declineReservation(Long scheduleId) throws MyExceptionClass {
        log.info("STARTED: " + " declineReservation ");
        log.info("SCHEDULE ID: " + scheduleId);
        Optional<Schedule> byId = scheduleRepo.findById(scheduleId);
        byId.ifPresent(schedule -> {
            schedule.setIsCompleted(-1);
            scheduleRepo.save(schedule);
        });
        log.info("SCHEDULE: " + byId);
        log.info("FINISHED: " + " declineReservation ");
        return getQueue(byId.get().getEvent().getEventId()).peek();
    }


    public boolean checkReserveExistence(Long scheduleId, Long studentId) throws MyExceptionClass {
        log.info("STARTED: " + " checkReserveExistence ");
        log.info("STUDENT ID: " + studentId + " SCHEDULE ID: " + scheduleId);
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow(()-> new MyExceptionClass("Schedule not found with ID: " + scheduleId));
        Event event = schedule.getEvent();
        boolean b = event.getSchedules().stream().anyMatch(schedule1 -> {
            Student student = schedule1.getStudent();
            if(student!=null && schedule1.getIsCompleted() == 0) {
                Long studentId1 = schedule1.getStudent().getStudentId();
                return studentId1.equals(studentId);
            }return false;
        });
        log.info("FINISHED: " + " checkReserveExistence ");
        return b;
    }
}

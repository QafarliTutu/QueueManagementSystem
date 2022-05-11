package org.asoiu.QueueManagementSystem.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.EventDto;
import org.asoiu.QueueManagementSystem.entity.Event;
import org.asoiu.QueueManagementSystem.entity.Schedule;
import org.asoiu.QueueManagementSystem.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Log4j2
@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepo;
    private final ScheduleService scheduleService;

    public Event createEvent(EventDto eventDto) throws ParseException {
        log.info("STARTED: " + " createEvent ");
        log.info("EVENTDTO: " + eventDto);
        Event event = new Event();
        event.setName(eventDto.getName());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        event.setStartDate(LocalDateTime.parse(eventDto.getStartDate(), dtf));
        event.setEndDate(LocalDateTime.parse(eventDto.getEndDate(), dtf));

        event.setWorkersNum(Integer.valueOf(eventDto.getWorkersNum()));
        event.setSchedules(scheduleService.createSchedule(event));

        log.info("EVENT: " + event);
        log.info("FINISHED: " + " createEvent ");
        return event;

    }

    public Event findEventById(Long eventId){
        log.info("STARTED: " + " findEventById ");
        log.info("ID: " + eventId);
        Event event = eventRepo.findById(eventId).orElseThrow(()-> new RuntimeException("Event not found with ID: " + eventId));
        log.info("EVENT: " + event);
        log.info("FINISHED: " + " findEventById ");
        return  event;
    }

    public List<Event> getAllEvents(){
        log.info("STARTED: " + " getAllEvents ");
        List<Event> events = eventRepo.findAll();
        log.info("EVENTS: " + events);
        log.info("FINISHED: " + " getAllEvents ");
        return events;
    }



}

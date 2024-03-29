package org.asoiu.QueueManagementSystem.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.EventDto;
import org.asoiu.QueueManagementSystem.dto.EventResp;
import org.asoiu.QueueManagementSystem.entity.Event;
import org.asoiu.QueueManagementSystem.entity.Schedule;
import org.asoiu.QueueManagementSystem.exception.MyExceptionClass;
import org.asoiu.QueueManagementSystem.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepo;
    private final ScheduleService scheduleService;

    public Event createEvent(EventDto eventDto) throws ParseException {
        log.info("STARTED: " + " createEvent ");
        log.info("EVENT DTO: " + eventDto);
        Event event = new Event();
        event.setName(eventDto.getName());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        event.setStartDate(LocalDateTime.parse(eventDto.getStartDate(), dtf));
        event.setEndDate(LocalDateTime.parse(eventDto.getEndDate(), dtf));

        event.setWorkersNum(eventDto.getWorkersNum()==null ? 0:Integer.valueOf(eventDto.getWorkersNum()));
        event.setDescription(eventDto.getDescription());
        event.setSchedules(scheduleService.createSchedule(event));
        log.info("FINISHED: " + " createEvent ");
        return eventRepo.save(event);

    }

    public EventResp findEventById(Long eventId) throws MyExceptionClass {
        log.info("STARTED: " + " findEventById ");
        log.info("ID: " + eventId);
        ModelMapper modelMapper = new ModelMapper();
        Event event = eventRepo.findById(eventId).orElseThrow(()-> new MyExceptionClass("Event not found with ID: " + eventId));
        log.info("EVENT: " + event);
        log.info("FINISHED: " + " findEventById ");
        return  modelMapper.map(event,EventResp.class);
    }

    public List<Event> getAllEvents(){
        log.info("STARTED: " + " getAllEvents ");
        List<Event> events = eventRepo.findAll().stream()
                .filter(event -> !event.getEndDate().minusMinutes(20).isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
        log.info("EVENTS: " + events);
        log.info("FINISHED: " + " getAllEvents ");
        return events;
    }

    public boolean deleteEvent(Long eventId){
        log.info("STARTED: " + " cancelEvent ");
        log.info("ID: " + eventId);
        AtomicBoolean result = new AtomicBoolean(false);
        eventRepo.findById(eventId).ifPresent(event -> {
            eventRepo.delete(event);
            result.set(true);
        });
        log.info("RESULT: " + result.get());
        log.info("FINISHED: " + " cancelEvent ");
        return result.get();
    }


}

package org.asoiu.QueueManagementSystem.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.EventDto;
import org.asoiu.QueueManagementSystem.entity.Event;
import org.asoiu.QueueManagementSystem.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        event.setStartDate(formatter.parse(eventDto.getStartDate()));
        event.setEndDate(formatter.parse(eventDto.getEndDate()));

        event.setWorkersNum(Integer.valueOf(eventDto.getWorkersNum()));
        event.setSchedules(scheduleService.createSchedule(event));

        log.info("EVENT: " + event);
        log.info("FINISHED: " + " createEvent ");
        return event;

    }

    public Event findEventById(Long id){
        log.info("STARTED: " + " findById ");
        log.info("ID: " + id);
        Event event = eventRepo.findById(id).orElseThrow(()-> new RuntimeException("Event not found with ID: " + id));
        log.info("FINISHED: " + " findById ");
        return  event;
    }

    public List<Event> getAllEvents(){
        log.info("STARTED: " + " getAllEvents ");
        List<Event> events = eventRepo.findAll();
        log.info("FINISHED: " + " getAllEvents ");
        return events;
    }
}

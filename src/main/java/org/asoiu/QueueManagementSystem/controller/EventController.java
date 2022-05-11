package org.asoiu.QueueManagementSystem.controller;

import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.EventDto;
import org.asoiu.QueueManagementSystem.dto.ServiceResponse;
import org.asoiu.QueueManagementSystem.entity.Employee;
import org.asoiu.QueueManagementSystem.entity.Event;
import org.asoiu.QueueManagementSystem.entity.Schedule;
import org.asoiu.QueueManagementSystem.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping ("/create")
    public ServiceResponse<Event> createEvent(@RequestBody EventDto eventDto) throws ParseException {
        log.info("CALLED: " + " createEvent " + "REQUEST BODY= " + eventDto.toString());
        Event event = eventService.createEvent(eventDto);
        return ServiceResponse.<Event>builder()
                .successful(true)
                .payload(event)
                .build();

    }

    @GetMapping("/all")
    public List<Event> getAllEvents(){
        log.info("CALLED: " + " getAllEvents " );
        return eventService.getAllEvents();
    }

    @GetMapping("/{eventId}")
    public Event findEventById(@PathVariable Long eventId) {
        log.info("CALLED: " + " findEventById " + "PATH VARIABLE= " + eventId);
        return eventService.findEventById(eventId);
    }



}

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
    public ServiceResponse<List<Event>> getAllEvents(){
        log.info("CALLED: " + " getAllEvents " );
        List<Event> allEvents = eventService.getAllEvents();
        return ServiceResponse.<List<Event>>builder()
                .successful(true)
                .payload(allEvents)
                .build();
    }

    @GetMapping("/{eventId}")
    public ServiceResponse<EventDto> findEventById(@PathVariable Long eventId) {
        log.info("CALLED: " + " findEventById " + "PATH VARIABLE= " + eventId);
        EventDto eventDto = eventService.findEventById(eventId);
        return ServiceResponse.<EventDto>builder()
                .successful(true)
                .payload(eventDto)
                .build();
    }

    @PutMapping("/{eventId}")
    public ServiceResponse<Boolean> deleteEvent(@PathVariable Long eventId){
        log.info("CALLED: " + " deleteEvent " + "PATH VARIABLE= " + eventId);
        Boolean result = eventService.deleteEvent(eventId);
        return ServiceResponse.<Boolean>builder()
                .successful(result)
                .payload(result)
                .build();
    }



}

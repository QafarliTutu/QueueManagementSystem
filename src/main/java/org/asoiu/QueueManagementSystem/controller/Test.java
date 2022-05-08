package org.asoiu.QueueManagementSystem.controller;

import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.EventDto;
import org.asoiu.QueueManagementSystem.entity.Event;
import org.asoiu.QueueManagementSystem.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Log4j2
@RestController
public class Test {

    private EventService eventService;

    public Test(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping ("/create/event")
    public Event createEvent(@RequestBody EventDto eventDto) throws ParseException {
        log.info("CALLED: " + " createEvent " + "REQUEST BODY= " + eventDto.toString());
        return eventService.createEvent(eventDto);
    }

    @GetMapping("/get/all")
    public List<Event> getAllEvents(){
        log.info("CALLED: " + " getAllEvents " );
        return eventService.getAllEvents();
    }

}

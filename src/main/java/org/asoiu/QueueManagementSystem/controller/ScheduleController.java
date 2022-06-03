package org.asoiu.QueueManagementSystem.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.ReserveDto;
import org.asoiu.QueueManagementSystem.dto.ScheduleDto;
import org.asoiu.QueueManagementSystem.dto.ServiceResponse;
import org.asoiu.QueueManagementSystem.entity.Employee;
import org.asoiu.QueueManagementSystem.entity.Schedule;
import org.asoiu.QueueManagementSystem.exception.MyExceptionClass;
import org.asoiu.QueueManagementSystem.service.ScheduleService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/all/{eventId}")
    public ServiceResponse<Map<LocalDate, List<ScheduleDto>>> getAllSchedules(@PathVariable Long eventId) throws MyExceptionClass {
        log.info("CALLED: " + " getAllSchedules " + "PATH VARIABLE= " + eventId);
        Map<LocalDate, List<ScheduleDto>> schedules = scheduleService.getAllSchedule(eventId);
        return ServiceResponse.<Map<LocalDate, List<ScheduleDto>>>builder()
                .successful(true)
                .payload(schedules)
                .build();
    }

    @PostMapping("/reserve")
    public ServiceResponse<?> makeReserve(@RequestBody ReserveDto reserveDto) throws MyExceptionClass {
        log.info("CALLED: " + " makeReserve " + "REQUEST BODY= " + reserveDto.toString());
        boolean b = scheduleService.checkReserveExistence(reserveDto.getScheduleId(), reserveDto.getStudentId());
        if(b) return ServiceResponse.<String>builder().successful(false).payload("You already have one schedule for this Event.").message("Something went wrong!").build();
        Schedule schedule = scheduleService.makeReserve(reserveDto.getStudentId(), reserveDto.getScheduleId());
        return ServiceResponse.<Schedule>builder()
                .successful(true)
                .payload(schedule)
                .build();

    }

    @PostMapping("/cancel")
    public ServiceResponse<Schedule> cancel(@RequestBody ReserveDto reserveDto) throws MyExceptionClass {
        log.info("CALLED: " + " cancel " + "REQUEST BODY= " + reserveDto.toString());
        Schedule schedule = scheduleService.cancelSchedule(reserveDto.getScheduleId());
        return ServiceResponse.<Schedule>builder()
                .successful(true)
                .payload(schedule)
                .build();
    }

    @GetMapping("/queue/{eventId}")
    public ServiceResponse<List<Schedule>> getQueue(@PathVariable Long eventId, @RequestParam(required = false, defaultValue = "") String name , @RequestParam(required = false, defaultValue = "") String pinCode) throws MyExceptionClass {
        List<Schedule> queue = scheduleService.getQueue(eventId, name, pinCode);
        return ServiceResponse.<List<Schedule>>builder()
                .successful(true)
                .payload(queue)
                .build();
    }

    @PostMapping("/queue/complete/{scheduleId}")
    public ServiceResponse<Schedule> completeReservation(@PathVariable Long scheduleId) throws MyExceptionClass {
        Schedule schedule = scheduleService.completeReservation(scheduleId);
        return ServiceResponse.<Schedule>builder()
                .successful(true)
                .payload(schedule)
                .build();
    }

    @PostMapping("/queue/decline/{scheduleId}")
    public ServiceResponse<Schedule> declineReservation(@PathVariable Long scheduleId) throws MyExceptionClass {
        Schedule schedule = scheduleService.declineReservation(scheduleId);
        return ServiceResponse.<Schedule>builder()
                .successful(true)
                .payload(schedule)
                .build();
    }



}

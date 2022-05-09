package org.asoiu.QueueManagementSystem.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asoiu.QueueManagementSystem.dto.ReserveDto;
import org.asoiu.QueueManagementSystem.entity.Schedule;
import org.asoiu.QueueManagementSystem.service.ScheduleService;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/all/{eventId}")
    public List<Schedule> getAllSchedules(@PathVariable Long eventId) {
        return scheduleService.getAllSchedule(eventId);
    }

    @PostMapping("/reserve")
    public Schedule makeReserve(@RequestBody ReserveDto reserveDto) {
        return scheduleService.makeReserve(reserveDto.getStudentId(), reserveDto.getScheduleId());
    }

    @PostMapping("/cancel")
    public Schedule cancel(@RequestBody ReserveDto reserveDto) {
        return scheduleService.cancelSchedule(reserveDto.getScheduleId());
    }

}

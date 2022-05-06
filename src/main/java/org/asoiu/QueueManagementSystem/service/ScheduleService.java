package org.asoiu.QueueManagementSystem.service;

import lombok.AllArgsConstructor;
import org.asoiu.QueueManagementSystem.entity.Event;
import org.asoiu.QueueManagementSystem.entity.Schedule;
import org.asoiu.QueueManagementSystem.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepo;

    public ScheduleService(ScheduleRepository scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public List<Schedule> createSchedule(Event event){
        List<Schedule> scheduleList = new ArrayList<>();
        Schedule schedule = new Schedule();
        schedule.setEvent(event);
        schedule.setAvailableDate(new Date());
        schedule.setIsAvailable(true);
        scheduleList.add(schedule);

        Schedule schedule1 = new Schedule();
        schedule1.setEvent(event);
        schedule1.setAvailableDate(new Date());
        schedule1.setIsAvailable(true);
        scheduleList.add(schedule1);
        return scheduleRepo.saveAll(scheduleList);

//        for(int i = 0;; i++){
//            Date date = event.getStartDate(); // 08 05 2022
//
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            date.
//            String strDate = dateFormat.format(date);
//            schedule.setAvailableDate();
//        }

    }
}

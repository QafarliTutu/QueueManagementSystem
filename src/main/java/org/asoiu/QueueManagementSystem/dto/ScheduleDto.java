package org.asoiu.QueueManagementSystem.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.asoiu.QueueManagementSystem.entity.Event;
import org.asoiu.QueueManagementSystem.entity.Student;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto implements Comparable<ScheduleDto>{

    private Long scheduleId;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime availableDate;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate availableDay;

    @JsonIgnore
    private Event event;

    private Student student;

    private Boolean isAvailable;

    private int isCompleted;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;

    private Boolean isPast;

    @Override
    public int compareTo(ScheduleDto o) {
        return Comparator.comparing(ScheduleDto::getScheduleId)
                .compare(o, this);
    }
}

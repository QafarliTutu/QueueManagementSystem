package org.asoiu.QueueManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@ToString(exclude = {"student", "event"})
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(name = "availableDate")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime availableDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id",nullable = false)
    @JsonIgnore
    private Event event;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "isAvailable")
    private Boolean isAvailable;

    @Column(name = "isCompleted")
    private int isCompleted;

    @Column(name = "createdDate", nullable = false, updatable = false)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdDate;


}

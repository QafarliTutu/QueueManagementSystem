package org.asoiu.QueueManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.PriorityQueue;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "createdDate", nullable = false, updatable = false)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "startDate")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startDate;

    @Column(name = "endDate")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime endDate;

    @Column(name = "workersNum")
    private Integer workersNum;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Collection<Schedule> schedules = new PriorityQueue<>();
}

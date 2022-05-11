package org.asoiu.QueueManagementSystem.entity;

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

    @Column(name = "createdDate", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "startDate")
    private LocalDateTime startDate;

    @Column(name = "endDate")
    private LocalDateTime endDate;

    @Column(name = "workersNum")
    private Integer workersNum;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Collection<Schedule> schedules = new PriorityQueue<>();
}

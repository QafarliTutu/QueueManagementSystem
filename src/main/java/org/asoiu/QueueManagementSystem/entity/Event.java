package org.asoiu.QueueManagementSystem.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @Temporal(value = TemporalType.DATE)
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "startDate")
    @Temporal(value = TemporalType.DATE)
    private Date startDate;

    @Column(name = "endDate")
    @Temporal(value = TemporalType.DATE)
    private Date endDate;

    @Column(name = "workersNum")
    private Integer workersNum;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Schedule> schedules;
}

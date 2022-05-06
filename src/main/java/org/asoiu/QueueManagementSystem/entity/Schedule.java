package org.asoiu.QueueManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
    @Temporal(value = TemporalType.DATE)
    private Date availableDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id",nullable = false)
    @JsonIgnore
    private Event event;

    @OneToOne(mappedBy = "schedule",cascade = CascadeType.ALL)
    private Student student;

    @Column(name = "isAvailable")
    private Boolean isAvailable;

    @Column(name = "createdDate", nullable = false, updatable = false)
    @Temporal(value = TemporalType.DATE)
    @CreationTimestamp
    private Date createdDate;
}

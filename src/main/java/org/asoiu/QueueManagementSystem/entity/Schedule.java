package org.asoiu.QueueManagementSystem.entity;

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
public class Schedule implements Comparable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(name = "availableDate")
    //@Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime availableDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id",nullable = false)
    @JsonIgnore
    private Event event;

    @OneToOne(mappedBy = "schedule",cascade = CascadeType.ALL)
    @JsonIgnore
    private Student student;

    @Column(name = "isAvailable")
    private Boolean isAvailable;

    @Column(name = "isCompleted")
    private int isCompleted;

    @Column(name = "createdDate", nullable = false, updatable = false)
    //@Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime createdDate;



    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

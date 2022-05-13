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
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "pinCode")
    private String pinCode;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL)
    @JsonIgnore
    private Schedule schedule;

    @Column(name = "createdDate", nullable = false, updatable = false)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdDate;


}

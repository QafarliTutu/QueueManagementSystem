package org.asoiu.QueueManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySchedulesResponse {

    private Long id;
    private String availableDate;
    private Long eventId;
    private Long studentId;



}

package org.asoiu.QueueManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    private String name;
    private String startDate;
    private String endDate;
    private String workersNum;
}

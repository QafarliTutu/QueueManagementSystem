package org.asoiu.QueueManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResp {

    private String name;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime endDate;


    private String workersNum;

    private String description;
}

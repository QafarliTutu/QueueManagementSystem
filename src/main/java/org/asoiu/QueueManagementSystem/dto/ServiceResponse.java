package org.asoiu.QueueManagementSystem.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResponse<T> {
    private boolean successful;
    private String message;
    private T payload;
}

package org.asoiu.QueueManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterStudentDto {
    private String email;
    private String name;
    private String pinCode;
    private String password;

}

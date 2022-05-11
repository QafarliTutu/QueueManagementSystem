package org.asoiu.QueueManagementSystem.exception;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class MyExceptionClass extends Exception{

    public MyExceptionClass(String message){
        super(message);
    }

}

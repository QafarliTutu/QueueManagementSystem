package org.asoiu.QueueManagementSystem.exception;

import lombok.AllArgsConstructor;
import org.asoiu.QueueManagementSystem.dto.ServiceResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@AllArgsConstructor
@ControllerAdvice
public class WholeAppException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request){
        ServiceResponse<String> bodyOfResponse = new ServiceResponse<>();
        bodyOfResponse.setSuccessful(false);
        bodyOfResponse.setMessage("Something went wrong!");
        bodyOfResponse.setPayload(ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}

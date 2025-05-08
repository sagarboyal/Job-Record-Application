package com.main.app.globalExceptionHandler;

import com.main.app.exception.ResourceFoundException;
import com.main.app.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleJobException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ResourceFoundException.class)
    public ResponseEntity<String> handleJobException(ResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        String allowedStatuses = String.join(", ", STATUS_MAP.keySet());
        String message = "Invalid status. Allowed values: " + allowedStatuses;
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    private static final Map<String, String> STATUS_MAP = Map.of(
            "ACCEPTED", "Accepted - Offer accepted",
            "APPLIED", "Application has been submitted",
            "INTERVIEW", "Interview is scheduled or completed",
            "OFFERED", "Job offer has been received",
            "REJECTED", "Application was rejected"
    );
}

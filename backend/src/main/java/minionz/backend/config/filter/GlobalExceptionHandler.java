package minionz.backend.config.filter;

import minionz.backend.common.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException ex) {

        ErrorResponse errorResponse = new ErrorResponse("접근 권한이 존재하지 않습니다.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
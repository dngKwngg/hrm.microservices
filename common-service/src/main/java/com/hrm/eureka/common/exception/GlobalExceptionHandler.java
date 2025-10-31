package com.hrm.eureka.common.exception;

import com.hrm.eureka.common.constants.ResponseCode;
import com.hrm.eureka.common.dto.CommonResponse;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle invalid username/password
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CommonResponse> handleBadCredentials(BadCredentialsException ex) {
        log.error("[Auth Error] Invalid username or password");
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new CommonResponse(ResponseCode.UNAUTHORIZED, "Invalid username or password"));
    }


    /**
     * Handle validation errors (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error("[Validation Error] {}", message);
        return ResponseEntity
                .badRequest()
                .body(new CommonResponse(ResponseCode.BAD_REQUEST, message));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CommonResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("[Not Found Error] {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new CommonResponse(ResponseCode.NOT_FOUND, ex.getMessage()));
    }

    /**
     * Handle other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleGenericException(Exception ex) {
        log.error("[Server Error]", ex);
        return ResponseEntity
                .internalServerError()
                .body(new CommonResponse(ResponseCode.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }
}

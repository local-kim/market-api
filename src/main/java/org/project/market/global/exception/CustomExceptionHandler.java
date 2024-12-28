package org.project.market.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {

        ErrorResponse errorResponse = ErrorResponse.builder()
            .code(e.getErrorCode().getCode())
            .message(e.getMessage())
            .build();

        log.error(e.getMessage(), e);

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
            .body(errorResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        ErrorResponse errorResponse = ErrorResponse.builder()
            .code(ErrorEnum.INTERNAL_SERVER_ERROR.getCode())
            .message(e.getMessage())
            .build();

        log.error(e.getMessage(), e);

        return ResponseEntity.status(ErrorEnum.INTERNAL_SERVER_ERROR.getHttpStatus())
            .body(errorResponse);
    }
}

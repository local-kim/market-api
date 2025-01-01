package org.project.market.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.project.market.global.response.CommonResponse;
import org.project.market.global.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<CommonResponse<ErrorResponse>> handleCustomException(CustomException e) {

        ErrorResponse errorResponse = ErrorResponse.builder()
            .code(e.getErrorEnum().getCode())
            .message(e.getErrorEnum().getMessage())
            .build();

        log.error(e.getMessage(), e);

        return ResponseEntity.status(e.getErrorEnum().getHttpStatus())
            .body(CommonResponse.fail(e.getMessage(), errorResponse));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CommonResponse<ErrorResponse>> handleException(Exception e) {

        ErrorResponse errorResponse = ErrorResponse.builder()
            .code(ErrorEnum.INTERNAL_SERVER_ERROR.getCode())
            .message(ErrorEnum.INTERNAL_SERVER_ERROR.getMessage())
            .build();

        log.error(e.getMessage(), e);

        return ResponseEntity.status(ErrorEnum.INTERNAL_SERVER_ERROR.getHttpStatus())
            .body(CommonResponse.fail(e.getMessage(), errorResponse));
    }

}

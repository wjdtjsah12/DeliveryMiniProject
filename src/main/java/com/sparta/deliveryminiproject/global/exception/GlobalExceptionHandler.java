package com.sparta.deliveryminiproject.global.exception;

import com.sparta.deliveryminiproject.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpServerErrorException.class)
  public ResponseEntity<ApiResponse> serverErrorHandler(HttpServerErrorException ex) {
    HttpStatusCode statusCode = ex.getStatusCode();
    String errorMessage = ex.getMessage();
    if (errorMessage.contains("he model is overloaded")) {
      errorMessage = "모델이 과부하되었습니다. 나중에 다시 시도해 주세요.";
    }
    return ResponseEntity.status(statusCode)
        .body(ApiResponse.error(statusCode.value(), errorMessage));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> handleValidationException(BindingResult bindingResult) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(bindingResult));
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity apiExceptionHandler(ApiException ex) {
    return ResponseEntity.status(ex.getStatus())
        .body(ApiResponse.error(ex.getStatus().value(), ex.getMsg()));
  }

}

package com.sparta.deliveryminiproject.global.exception;

import com.sparta.deliveryminiproject.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> handleValidationException(BindingResult bindingResult) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(bindingResult));
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiResponse> apiExceptionHandler(ApiException ex) {
    return ResponseEntity.status(ex.getStatus()).body(ApiResponse.error(ex.getMsg()));
  }

}

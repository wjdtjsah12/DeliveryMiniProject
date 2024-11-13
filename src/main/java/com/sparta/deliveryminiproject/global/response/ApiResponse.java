package com.sparta.deliveryminiproject.global.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

  private int statusCode;
  private String message;

  public static ApiResponse<?> fail(BindingResult bindingResult) {
    List<ObjectError> allErrors = bindingResult.getAllErrors();
    return new ApiResponse<>(400, allErrors.get(0).getDefaultMessage());
  }

  public static ApiResponse<?> error(int statusCode, String message) {
    return new ApiResponse<>(statusCode, message);
  }

  private ApiResponse(int statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }
}
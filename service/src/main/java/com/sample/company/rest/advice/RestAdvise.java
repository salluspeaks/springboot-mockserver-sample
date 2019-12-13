package com.sample.company.rest.advice;

import com.sample.company.exception.DataValidationException;
import com.sample.company.model.ApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestAdvise extends ResponseEntityExceptionHandler {

  @ExceptionHandler({DataValidationException.class})
  public ResponseEntity<ApiExceptionResponse> handleDataValidation(DataValidationException ex){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiExceptionResponse.builder().errorCode("111").description(ex.getMessage()).build());

  }


}

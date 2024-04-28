package com.o.nimko.testforclearsolutin.conrollers;

import com.o.nimko.testforclearsolutin.dto.ResponseDetails;
import com.o.nimko.testforclearsolutin.dto.ValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler({NoSuchElementException.class})
  @ResponseBody
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ResponseDetails<?> notFoundRequest(Exception e, HttpServletRequest request) {
    log.error("Error during request execution. Host {}", request.getRemoteAddr() + " "
        + request.getRemoteHost(), e);
    return new ResponseDetails<>().setError(e.getMessage())
        .setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseBody
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ResponseDetails<?> failedValidationProcessingRequest(MethodArgumentNotValidException e,
      HttpServletRequest request) {
    log.error("Failed to process content. Host {}", request.getRemoteAddr() + " "
        + request.getRemoteHost(), e);
    List<ValidationResult> validationResults = e.getBindingResult().getFieldErrors().stream()
        .map(er -> new ValidationResult().setField(er.getField())
            .setMessage(er.getDefaultMessage()))
        .collect(Collectors.toList());

    return new ResponseDetails<>()
        .setValidation(validationResults)
        .setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
  }

  @ExceptionHandler({Exception.class})
  @ResponseBody
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseDetails<?> failedRequest(Exception e, HttpServletRequest request) {
    log.error("Error during request execution. Host {}", request.getRemoteAddr() + " "
        + request.getRemoteHost(), e);
    return new ResponseDetails<>().setError(e.getMessage())
        .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
  }
}

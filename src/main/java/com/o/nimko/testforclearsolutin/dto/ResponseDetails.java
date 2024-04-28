package com.o.nimko.testforclearsolutin.dto;

import static com.o.nimko.testforclearsolutin.utils.DateUtils.now;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDetails<T> {

  private String id = UUID.randomUUID().toString();
  private String datetime = now();
  private String status;
  private T body;
  private List<ValidationResult> validation;
  private String error;
}

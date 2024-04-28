package com.o.nimko.testforclearsolutin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ValidationResult {

  private String field;
  private String message;
}

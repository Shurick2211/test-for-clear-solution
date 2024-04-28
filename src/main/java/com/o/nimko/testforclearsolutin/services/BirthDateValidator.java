package com.o.nimko.testforclearsolutin.services;


import static java.time.LocalDate.now;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class BirthDateValidator implements
    ConstraintValidator<BirthDate, LocalDate> {

  @Value("${person.age.min}")
  private int minAge;

  @Override
  public boolean isValid(LocalDate localDate,
      ConstraintValidatorContext constraintValidatorContext) {
    return localDate.isBefore(now()) && validateAge(localDate);
  }

  private boolean validateAge(LocalDate birthDate) {
    var now = now();
    var age = now.getYear() - birthDate.getYear();
    log.debug("Age is {}", age);
    if (age == minAge) {
      return now.getMonth().getValue() >  birthDate.getMonth().getValue() ||
          (now.getMonth().getValue() ==  birthDate.getMonth().getValue() && now.getDayOfMonth() >= birthDate.getDayOfMonth());
    }
    return age >= minAge;
  }
}

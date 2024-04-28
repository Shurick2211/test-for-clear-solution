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
  private int minAge = 18;

  @Override
  public boolean isValid(LocalDate localDate,
      ConstraintValidatorContext constraintValidatorContext) {
    return localDate.isBefore(now()) && validateAge(localDate);
  }

  private boolean validateAge(LocalDate birthDate) {
    log.info("Min age is {}", minAge);
    var now = now();
    var age = now.getYear() - birthDate.getYear();
    if (age == (minAge - 1)) {
      return now.getMonth().getValue() >  birthDate.getMonth().getValue() ||
          (now.getMonth().getValue() ==  birthDate.getMonth().getValue() && now.getDayOfMonth() >= birthDate.getDayOfMonth());
    }
    return age >= minAge;
  }
}

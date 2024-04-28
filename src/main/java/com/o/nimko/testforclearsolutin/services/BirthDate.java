package com.o.nimko.testforclearsolutin.services;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Constraint(validatedBy = BirthDateValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface BirthDate {

  String message() default "Date of birth invalid!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

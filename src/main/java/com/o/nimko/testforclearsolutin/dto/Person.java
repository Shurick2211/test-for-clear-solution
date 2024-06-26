package com.o.nimko.testforclearsolutin.dto;

import com.o.nimko.testforclearsolutin.services.BirthDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

  @Email
  private String email;
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  @NotNull
  @BirthDate
  private LocalDate birthDate;
  private String address;
  private String phoneNumber;

}

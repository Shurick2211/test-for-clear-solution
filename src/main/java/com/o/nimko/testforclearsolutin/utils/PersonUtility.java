package com.o.nimko.testforclearsolutin.utils;

import com.o.nimko.testforclearsolutin.dto.Person;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PersonUtility {

  public static void patchUtil(Person existingPerson, Person newPerson) {
    if (newPerson.getEmail() != null && !newPerson.getEmail().equals(existingPerson.getEmail())) {
      existingPerson.setEmail(newPerson.getEmail());
    }
    if (newPerson.getFirstName() != null && !newPerson.getFirstName().equals(existingPerson.getFirstName())) {
      existingPerson.setFirstName(newPerson.getFirstName());
    }
    if (newPerson.getLastName() != null && !newPerson.getLastName().equals(existingPerson.getLastName())) {
      existingPerson.setLastName(newPerson.getLastName());
    }
    if (newPerson.getBirthDate() != null && !newPerson.getBirthDate().equals(existingPerson.getBirthDate())) {
      existingPerson.setBirthDate(newPerson.getBirthDate());
    }
    if (newPerson.getAddress() != null && !newPerson.getAddress().equals(existingPerson.getAddress())) {
      existingPerson.setAddress(newPerson.getAddress());
    }
    if (newPerson.getPhoneNumber() != null && !newPerson.getPhoneNumber().equals(existingPerson.getPhoneNumber())) {
      existingPerson.setPhoneNumber(newPerson.getPhoneNumber());
    }
  }
}

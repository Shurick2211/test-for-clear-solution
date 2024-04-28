package com.o.nimko.testforclearsolutin.services;

import com.o.nimko.testforclearsolutin.dto.Person;
import java.util.List;

public interface PersonService {

  int createPerson(Person person);

  List<Person> getPersonByDateRange(String from, String to);

  void patchPerson(int id, Person person);

  void updatePerson(int id, Person person);

  void deletePerson(int id);
}

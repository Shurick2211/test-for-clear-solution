package com.o.nimko.testforclearsolutin.services;

import static com.o.nimko.testforclearsolutin.utils.DateUtils.parseDate;
import static com.o.nimko.testforclearsolutin.utils.PersonUtility.patchUtil;

import com.o.nimko.testforclearsolutin.dto.Person;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

  private final Map<Integer, Person> persons = new LinkedHashMap<>();

  @Override
  public int createPerson(Person person) {
    var id = persons.size();
    persons.put(id, person);
    return id;
  }

  @Override
  public List<Person> getPersonByDateRange(String from, String to) {
    return persons.values().stream()
        .filter(person -> person.getBirthDate().isAfter(parseDate(from)) && person.getBirthDate()
            .isBefore(parseDate(to)))
        .toList();
  }

  @Override
  public void patchPerson(int id, Person person) {
    var oldPerson = checkPersonForNull(id);
    patchUtil(oldPerson, person);
  }

  @Override
  public void updatePerson(int id, Person person) {
    checkPersonForNull(id);
    persons.put(id, person);
  }

  @Override
  public void deletePerson(int id) {
    checkPersonForNull(id);
    persons.remove(id);
  }

  private Person checkPersonForNull(int id) {
    var person = persons.get(id);
    if (person == null) {
      throw new NoSuchElementException();
    }
    return person;
  }
}

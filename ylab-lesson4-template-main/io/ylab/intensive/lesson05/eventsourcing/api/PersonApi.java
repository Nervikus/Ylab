package io.ylab.intensive.lesson05.eventsourcing.api;

import java.util.List;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

@Component
public interface PersonApi {

  void deletePerson(Long personId);

  void savePerson(Long personId, String firstName, String lastName, String middleName);

  Person findPerson(Long personId);

  List<Person> findAll();
}

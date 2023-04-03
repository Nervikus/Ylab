package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Между вызовами некоторых команд установил паузу в 1 сек., чтобы база успела обработать данные
 */

public class ApiApp {
  public static void main(String[] args) throws Exception {
    // Тут создание PersonApi, запуск и демонстрация работы
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();
    PersonApi personApi = applicationContext.getBean(PersonApi.class);
    personApi.savePerson(1L, "Egiian", "Armen", "Nikolaevich");
    personApi.savePerson(2L, "Ylab", "Company", "");
    personApi.findPerson(1L);
    personApi.savePerson(3L, "Hello", "World", "!!!");
    personApi.findAll();
    personApi.savePerson(3L, "Hop", "Hey", "Na-Na-Ney!");
    personApi.findAll();
    personApi.deletePerson(1L);
    personApi.findAll();
    System.out.println(personApi.findPerson(1L));
    personApi.savePerson(1L, "Mr.", "Incognito", "@Nervikus");
    personApi.deletePerson(3L);
    personApi.deletePerson(2L);
    Thread.sleep(1000);
    personApi.findAll();
    personApi.deletePerson(1L);
    personApi.findAll();
    personApi.deletePerson(1L);
    personApi.deletePerson(1L);
    personApi.findAll();

    System.out.println();

    // демонстрация возвращения нуля, вместо объекта Person
    personApi.savePerson(1L, "Test", "123", "qwerty");
    Thread.sleep(1000);
    Person person = personApi.findPerson(1L);
    personApi.deletePerson(1L);
    Thread.sleep(1000);
    person = personApi.findPerson(1L);
    System.out.println(person);
  }
}

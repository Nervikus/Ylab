package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

@Component
public interface DataProcessor {

    void handleMessage();

    void deletePerson(Person person);

    void savePerson(Person person);
}

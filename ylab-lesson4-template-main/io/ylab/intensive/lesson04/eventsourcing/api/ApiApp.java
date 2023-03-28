package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

/**
 * Я переопределил метод toString у класса Person
 * и вызвал вывод в консоль в методах класса PersonApiImpl,
 * чтобы здесь не загромождать код добавлением System.out.println()
 */

public class ApiApp {
    public static void main(String[] args) throws Exception {
        // Тут создание PersonApi, запуск и демонстрацию работы
        ConnectionFactory connectionFactory = initMQ();
        PersonApi personApi = new PersonApiImpl(connectionFactory);
        personApi.savePerson(1L, "Egiian", "Armen", "Nikolaevich");
        personApi.savePerson(2L, "Ylab", "Company", "");
        personApi.findPerson(1L);
        personApi.savePerson(3L, "Hello", "World", "!!!");
        personApi.findAll();
        personApi.savePerson(3L, "Hop", "Hey", "Na-Na-Ney!");
        personApi.findAll();
        personApi.deletePerson(1L);
        personApi.findAll();
        personApi.findPerson(1L);
        personApi.savePerson(1L, "Mr.", "Incognito", "@Nervikus");
        personApi.deletePerson(3L);
        personApi.deletePerson(2L);
        personApi.findAll();
        personApi.deletePerson(1L);
        personApi.findAll();
        personApi.deletePerson(1L);
        personApi.deletePerson(1L);
        personApi.findAll();

        System.out.println();

        // демонстрация возвращения нуля, вместо объекта Person
        personApi.savePerson(1L, "Test", "123", "qwerty");
        Person person = personApi.findPerson(1L);
        personApi.deletePerson(1L);
        person = personApi.findPerson(1L);
        System.out.println(person);
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}

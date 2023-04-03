package io.ylab.intensive.lesson05.eventsourcing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Component
public class PersonApiImpl implements PersonApi {
    private ConnectionFactory connectionFactory;
    private DataSource dataSource;
    private String exchangeName = "exc";
    private String queue = "queue";
    private Person person;

    @Autowired
    public PersonApiImpl(ConnectionFactory connectionFactory, DataSource dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) {
        person = new Person();
        person.setId(personId);
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            String delete_key = "delete";
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exchangeName, "*");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(person) + ";" + delete_key;
            channel.basicPublish("", queue, null, json.getBytes());
            System.out.println("Удаляем данные человека, чей id=" + personId);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {

        person = new Person(personId, firstName, lastName, middleName);
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            String save_key = "save";
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exchangeName, "*");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(person) + ";" + save_key;
            channel.basicPublish("", queue, null, json.getBytes());
            System.out.println("Сохраняем в базу " + person);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Person findPerson(Long personId) {
        person = new Person();
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM person WHERE person_id = ?")) {
            if (personId == null) {
                return null;
            } else {
                statement.setLong(1, personId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        person.setId(resultSet.getLong(1));
                        person.setName(resultSet.getString(2));
                        person.setLastName(resultSet.getString(3));
                        person.setMiddleName(resultSet.getString(4));
                        System.out.println(person);
                        return person;
                    } else {
                        return null; // возвращает null вместо объекта Person
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT person_id FROM person")) {
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Список людей в базе: ");

            while (resultSet.next()) {
                person = findPerson(resultSet.getLong(1));
                people.add(person);
            }

            if (people.size() <= 0) {
                System.out.println(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }
}


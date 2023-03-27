package io.ylab.intensive.lesson04.eventsourcing.api;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;

public class PersonApiImpl implements PersonApi {
    private ConnectionFactory connectionFactory;
    private String exchangeName = "exc";
    private Person person;

    public PersonApiImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void deletePerson(Long personId) {
        person = new Person();
        person.setId(personId);
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            String queueDelete = "delete";
            channel.queueDeclare(queueDelete, true, false, false, null);
            channel.queueBind(queueDelete, exchangeName, "*");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(person);
            channel.basicPublish("", queueDelete, null, json.getBytes());
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
            String queueSave = "save";
            channel.queueDeclare(queueSave, true, false, false, null);
            channel.queueBind(queueSave, exchangeName, "*");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(person);
            channel.basicPublish("", queueSave, null, json.getBytes());
            System.out.println("Отправлено: " + json);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Person findPerson(Long personId) {
        Person person = new Person();
        try {
            DataSource dataSource = DbUtil.buildDataSource();
            try (java.sql.Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "SELECT * FROM person WHERE person_id = ?")) {
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
                        System.out.println("В базе данных нет человека с id=" + personId);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();
        try {
            DataSource dataSource = DbUtil.buildDataSource();
            try (java.sql.Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "SELECT person_id FROM person")) {
                ResultSet resultSet = statement.executeQuery();
                System.out.println("Список людей в базе: ");

                while (resultSet.next()) {
                    Person person = findPerson(resultSet.getLong(1));
                    people.add(person);
                }

                if (people.size() <= 0) {
                    System.out.println(0);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }
}
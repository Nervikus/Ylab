package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DataProcessorImpl implements DataProcessor {
    private DataSource dataSource;
    private ConnectionFactory connectionFactory;
    private static final Logger LOGGER = Logger.getLogger(DataProcessorImpl.class.getName());

    @Autowired
    public DataProcessorImpl(DataSource dataSource, ConnectionFactory connectionFactory) {
        this.dataSource = dataSource;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void handleMessage() {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            ObjectMapper objectMapper = new ObjectMapper();
            Person person;
            try {
                String queueName = "queue";
                channel.queueDeclare(queueName, true, false, false, null);
                while (!Thread.currentThread().isInterrupted()) {
                    GetResponse messageForSave = channel.basicGet(queueName, true);
                    if (messageForSave != null) {
                        String[] json = new String(messageForSave.getBody()).split(";");
                        person = objectMapper.readValue(json[0].trim(), Person.class);
                        String message = json[1];
                        if (message.equals("save")) {
                            savePerson(person);
                        } else if (message.equals("delete")) {
                            deletePerson(person);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePerson(Person person) {
        Long personId = person.getId();
        String SQL = "SELECT * FROM person WHERE person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            if (personId != null) { // добавил проверку на null
                statement.setLong(1, personId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String deleteSQL = "DELETE FROM person WHERE person_id = ?";
                        PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL);
                        deleteStatement.setLong(1, personId);
                        deleteStatement.executeUpdate();
                        deleteStatement.close();
                        LOGGER.log(Level.INFO, "Person (ID:" + personId + ") deleted");
                    } else {
                        LOGGER.log(Level.INFO,"Person (ID:" + personId + ") is not found to delete");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void savePerson(Person person) {
        Long personId = person.getId();
        String name = person.getName();
        String lastName = person.getLastName();
        String middleName = person.getMiddleName();
        String SQL = "SELECT * FROM person WHERE person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setLong(1, personId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String updateSQL = "UPDATE person SET first_name = ?, last_name = ?, middle_name = ? WHERE person_id = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateSQL);
                    updateStatement.setString(1, name);
                    updateStatement.setString(2, lastName);
                    updateStatement.setString(3, middleName);
                    updateStatement.setLong(4, personId);
                    updateStatement.executeUpdate();
                    updateStatement.close();
                    LOGGER.log(Level.INFO, "Person (ID:" + personId + ") updated");
                } else {
                    String insertSQL = "INSERT INTO person (person_id, first_name, last_name, middle_name) VALUES (?, ?, ?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
                    insertStatement.setLong(1, personId);
                    insertStatement.setString(2, name);
                    insertStatement.setString(3, lastName);
                    insertStatement.setString(4, middleName);
                    insertStatement.executeUpdate();
                    insertStatement.close();
                    LOGGER.log(Level.INFO, "Person (ID:" + personId + ") inserted");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageFilterImpl implements MessageFilter {
    private DataSource dataSource;
    private ConnectionFactory connectionFactory;

    @Autowired
    public MessageFilterImpl(DataSource dataSource, ConnectionFactory connectionFactory) {
        this.dataSource = dataSource;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void messageProcess() {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            String queueName = "input";
            channel.queueDeclare(queueName, true, false, false, null);
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse input = channel.basicGet(queueName, true);
                if (input != null) {
                    String message = new String(input.getBody());
                    String censorMessage = filter(message);
                    sendMessage(censorMessage);
                }
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            String exchangeName = "censor_exc";
            String queueName = "output";
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, "*");
            channel.basicPublish(exchangeName, "*", null, message.getBytes());
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String filter(String message) {
        StringBuilder sb = new StringBuilder();
        if (message != null) {
            String[] words = message.split("(?<=\\s)|(?=\\s)|(?<=[ ,.;?!])|(?=[ ,.;?!])");
//            Если использовать regex ниже, то мы найдем брань среди всех знаков препинания
//            String[] words = message.split("(?<=\\p{Punct}|\\s)|(?=\\p{Punct}|\\s)");
            try (java.sql.Connection connection = dataSource.getConnection()) {
                for (String word : words) {
                    String selectSQL = "SELECT * FROM censor WHERE word ILIKE ?";
                    PreparedStatement statement = connection.prepareStatement(selectSQL);
                    statement.setString(1, word.trim());
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (!resultSet.next()) {
                            sb.append(word);
                        } else {
                            sb.append(word.charAt(0));
                            sb.append("*".repeat(Math.max(0, word.length() - 2)));
                            sb.append(word.charAt(word.length() - 1));
                        }
                    }
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}

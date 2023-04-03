package io.ylab.intensive.lesson05.messagefilter.sender;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class SenderImpl implements Sender {
    private ConnectionFactory connectionFactory;

    public SenderImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void sendToInput(String message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            String exchangeName = "sender_exc";
            String queueName = "input";
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, "*");
            channel.basicPublish(exchangeName, queueName, null, message.getBytes());
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}

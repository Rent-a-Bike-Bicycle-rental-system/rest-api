package com.example.gate.rabbitmq;

import com.example.data.data.Application;
import com.example.data.database.DatabaseInterface;
import com.example.gate.rabbitmq.interfaces.RabbitMQSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RabbitMQGetter {
    private final DatabaseInterface database;
    private final RabbitMQSender rabbitMQSender;

    public RabbitMQGetter(@Qualifier("postgreSqlDatabase") DatabaseInterface database, RabbitMQSender rabbitMQSender) {
        this.database = database;
        this.rabbitMQSender = rabbitMQSender;
    }

    @RabbitListener(queues = "${rabbitmq.queues.application}")
    @Transactional
    public void receiveApplication(Application application) {
        database.addNewApplication(application);

        rabbitMQSender.sendApplicationToAdministratorsUsingTelegram(application);
        rabbitMQSender.sendApplicationToEmail(application);
    }
}

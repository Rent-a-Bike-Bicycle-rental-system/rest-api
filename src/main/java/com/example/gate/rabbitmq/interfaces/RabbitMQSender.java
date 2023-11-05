package com.example.gate.rabbitmq.interfaces;

import com.example.data.data.Application;

public interface RabbitMQSender {
    void sendApplicationToAdministratorsUsingTelegram(Application application);
    void sendApplicationToEmail(Application application);
}

package com.example.gate.rabbitmq;

import com.example.data.data.Application;
import com.example.data.services.ApplicationService;
import com.example.gate.rabbitmq.interfaces.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService implements RabbitMQSender {
    private final ApplicationService applicationService;

    @Autowired
    public RabbitMQService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }


    @Override
    public void sendApplicationToAdministratorsUsingTelegram(Application application) {
        applicationService.sendToTelegram(application);
    }

    @Override
    public void sendApplicationToEmail(Application application) {
        applicationService.sendToEmail(application);
    }
}

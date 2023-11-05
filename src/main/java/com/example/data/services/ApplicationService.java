package com.example.data.services;

import com.example.data.data.Application;
import com.example.data.repositories.ApplicationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.keys.telegram}")
    private String telegramQueueKey;

    @Value("${rabbitmq.keys.email}")
    private String emailQueueKey;

    @Value("${rabbitmq.exchanges.main}")
    private String exchange;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, RabbitTemplate rabbitTemplate) {
        this.applicationRepository = applicationRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void addNewApplication(Application application) {
        applicationRepository.save(application);
    }

    public void sendToTelegram(Application application) {
        rabbitTemplate.convertAndSend(exchange, telegramQueueKey, application);
    }

    public void sendToEmail(Application application) {
        rabbitTemplate.convertAndSend(exchange, emailQueueKey, application);
    }
}

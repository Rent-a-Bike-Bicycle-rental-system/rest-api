package com.example.gate.restapi;

import com.example.data.data.Application;
import com.example.data.data.Bike;
import com.example.data.data.City;
import com.example.data.database.DatabaseInterface;
import com.example.gate.rabbitmq.interfaces.RabbitMQSender;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final DatabaseInterface database;
    private final RabbitMQSender rabbitMQSender;

    @Autowired
    public UserController(@Qualifier("postgreSqlDatabase") DatabaseInterface database, RabbitMQSender rabbitMQSender) {
        this.database = database;
        this.rabbitMQSender = rabbitMQSender;
    }

    @GetMapping("/get_bikes")
    @PermitAll
    public ResponseEntity<List<Bike>> getBikes() {
        List<Bike> bikes = database.getBikes();

        return ResponseEntity.ok(bikes);
    }

    @GetMapping("/get_cities")
    @PermitAll
    public ResponseEntity<List<City>> getCities() {
        List<City> cities = database.getCities();

        return ResponseEntity.ok(cities);
    }

    @PostMapping("/send_application")
    @PermitAll
    @Transactional
    public ResponseEntity<String> sendApplication(@RequestBody Application application) {
        if(!application.isGoodApplicationData())
            return ResponseEntity.status(402).body("Bad data");

        database.addNewApplication(application);
        rabbitMQSender.sendApplicationToAdministratorsUsingTelegram(application);
        rabbitMQSender.sendApplicationToEmail(application);

        return ResponseEntity.ok("Application saved!");
    }
}

package com.example.gate.restapi;

import com.example.data.data.Application;
import com.example.data.data.Bike;
import com.example.data.data.City;
import com.example.data.database.DatabaseInterface;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final DatabaseInterface database;

    public UserController(@Qualifier("postgreSqlDatabase") DatabaseInterface database) {
        this.database = database;
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
    public ResponseEntity<String> sendApplication(@RequestBody Application application) {
        System.out.println(application);
        if(!application.isGoodApplicationData())
            return ResponseEntity.status(402).body("Bad data");



        boolean savedApplication = database.addNewApplication(application);

        if(savedApplication)
            return ResponseEntity.ok("Application saved!");
        else
            return ResponseEntity.status(402).body("Bad parameter");
    }
}

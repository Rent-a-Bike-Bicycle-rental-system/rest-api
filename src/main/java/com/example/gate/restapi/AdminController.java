package com.example.gate.restapi;

import com.example.data.data.*;
import com.example.data.database.DatabaseInterface;
import com.example.exceptions.IncorrectPasswordException;
import com.example.security.jwt.JwtTokenProvider;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final DatabaseInterface databaseInterface;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AdminController(DatabaseInterface databaseInterface, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.databaseInterface = databaseInterface;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<String> loginAdmin(@RequestBody Admin admin) {
        try {
            var user = userDetailsService.loadUserByUsername(admin.getLogin());

            if(!passwordEncoder.matches(admin.getPassword(), user.getPassword()))
                throw new IncorrectPasswordException("Incorrect password");

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(401).body("User not found");
        } catch (IncorrectPasswordException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }


        try {
            String token = jwtTokenProvider.createRefreshToken(admin.getId(), admin.getLogin());

            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }

    @GetMapping("/applications")
    public ResponseEntity<?> getApplications(@RequestBody ApplicationRequest request) {
        List<Application> applications = databaseInterface.getApplications(request);
        if(applications == null)
            return ResponseEntity.status(400).body("Bad request");
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/bike")
    public ResponseEntity<String> addNewBike(@RequestBody Bike bike) {
        if (bike.isBadBikeData())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad data");

        boolean isAdded = databaseInterface.addNewBike(bike);
        return isAdded
                ? ResponseEntity.ok("Bike added successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add bike");
    }

    @PatchMapping("/bike")
    public ResponseEntity<String> changeBikeInfo(@RequestBody Bike bike) {

        if (bike.isBadBikeData())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad data");

        boolean isChanged = databaseInterface.changeBikeInfo(bike);
        return isChanged
                ? ResponseEntity.ok("Bike info updated successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update bike info");
    }

    @DeleteMapping("/bike")
    public ResponseEntity<String> deleteBike(@RequestBody Bike bike) {
        if (bike.isBadBikeData())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad data");

        boolean isDeleted = databaseInterface.deleteBike(bike.getId());
        return isDeleted
                ? ResponseEntity.ok("Bike deleted successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete bike");
    }

    @PostMapping("/city")
    public ResponseEntity<String> addNewCity(@RequestBody City city) {
        if (city.isBadCityData())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad data");

        boolean isAdded = databaseInterface.addNewCity(city);
        return isAdded
                ? ResponseEntity.ok("City added successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add city");
    }

    @DeleteMapping("/city")
    public ResponseEntity<String> deleteCity(@RequestBody City city) {
        boolean isDeleted = databaseInterface.deleteCity(city.getId());
        return isDeleted
                ? ResponseEntity.ok("City deleted successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete city");
    }

    @PostMapping("/new_admin")
    public ResponseEntity<String> newAdmin(@RequestBody Admin admin) {
        if (admin.isBadAdminData())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad data");

        boolean isLoginExists = databaseInterface.isAdminLoginExists(admin.getLogin());
        if (isLoginExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin login already exists");
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        boolean isRegistered = databaseInterface.registerAdmin(admin);
        return isRegistered
                ? ResponseEntity.ok("New admin registered successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to register admin");
    }
}

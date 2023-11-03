package com.example.gate.restapi;

import com.example.security.JwtTokenProvider;
import com.example.data.data.*;
import com.example.data.database.DatabaseInterface;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AuthenticationManager authenticationManager;
    private final DatabaseInterface databaseInterface;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public AdminController(AuthenticationManager authenticationManager, DatabaseInterface databaseInterface, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.databaseInterface = databaseInterface;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<String> loginAdmin(@RequestBody Admin admin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(admin.getLogin(), admin.getPassword())
            );

            // Create JWT token
            String token = jwtTokenProvider.createRefreshToken(admin.getId(), admin.getLogin());

            // Build response with JWT token
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }

    @GetMapping("/applications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Application>> getApplications(@RequestBody ApplicationReqest request) {
        List<Application> applications = databaseInterface.getApplications(request);
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/bike")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addNewBike(@RequestBody Bike bike) {
        if (bike.isBadBikeData())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad data");

        boolean isAdded = databaseInterface.addNewBike(bike);
        return isAdded
                ? ResponseEntity.ok("Bike added successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add bike");
    }

    @PatchMapping("/bike")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeBikeInfo(@RequestBody Bike bike) {
        if (bike.isBadBikeData())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad data");

        boolean isChanged = databaseInterface.changeBikeInfo(bike);
        return isChanged
                ? ResponseEntity.ok("Bike info updated successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update bike info");
    }

    @DeleteMapping("/bike")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBike(@RequestBody Bike bike) {
        if (bike.isBadBikeData())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad data");

        boolean isDeleted = databaseInterface.deleteBike(bike.getId());
        return isDeleted
                ? ResponseEntity.ok("Bike deleted successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete bike");
    }

    @PostMapping("/city")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addNewCity(@RequestBody City city) {
        if (!city.isGoodCityData())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad data");

        boolean isAdded = databaseInterface.addNewCity(city);
        return isAdded
                ? ResponseEntity.ok("City added successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add city");
    }

    @DeleteMapping("/city")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCity(@RequestBody City city) {
        if (!city.isGoodCityData())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad data");

        boolean isDeleted = databaseInterface.deleteCity(city.getId());
        return isDeleted
                ? ResponseEntity.ok("City deleted successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete city");
    }

    @PostMapping("/new_admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> newAdmin(@RequestBody Admin admin) {
        if (admin.isBadAdminData())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad data");

        boolean isLoginExists = databaseInterface.isAdminLoginExists(admin.getLogin());
        if (isLoginExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin login already exists");
        }

        // Шифруем пароль перед сохранением
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        boolean isRegistered = databaseInterface.registerAdmin(admin);
        return isRegistered
                ? ResponseEntity.ok("New admin registered successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to register admin");
    }
}

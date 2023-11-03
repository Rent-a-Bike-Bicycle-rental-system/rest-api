package com.example.data.services;

import com.example.data.data.Bike;
import com.example.data.repositories.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BikeService {
    private final BikeRepository bikeRepository;

    @Autowired
    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public boolean updateBike(Bike updatedBike) {
        Optional<Bike> existingBike = bikeRepository.findById(updatedBike.getId());

        if (existingBike.isPresent()) {
            bikeRepository.save(updatedBike);

            return true;
        } else {
            return false;
        }
    }

    public boolean addNewBike(Bike bike) {
        Optional<Bike> existingBike = bikeRepository.findById(bike.getId());

        if (existingBike.isEmpty()) {
            bikeRepository.save(bike);

            return true;
        } else {
            return false;
        }
    }
}
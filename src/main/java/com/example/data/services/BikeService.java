package com.example.data.services;

import com.example.data.data.Bike;
import com.example.data.data.BikePhoto;
import com.example.data.repositories.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BikeService {
    private final BikeRepository bikeRepository;

    @Autowired
    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public List<BikePhoto> updateBike(Bike updatedBike) {
        Optional<Bike> existingBike = bikeRepository.findById(updatedBike.getId());

        if (existingBike.isPresent()) {
            List<BikePhoto> bikePhotoList = new ArrayList<>(existingBike.get().getPhotos());
            bikeRepository.save(updatedBike);

            return bikePhotoList;
        } else {
            return null;
        }
    }

    public int addNewBike(Bike bike) {
        Optional<Bike> existingBike = bikeRepository.findById(bike.getId());

        if (existingBike.isEmpty()) {
            Bike savedBike = bikeRepository.save(bike);

            return savedBike.getId();
        } else {
            return Integer.MIN_VALUE;
        }
    }
}
package com.example.data.services;

import com.example.data.data.BikePhoto;
import com.example.data.repositories.BikePhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BikePhotoService {
    private final BikePhotoRepository bikePhotoRepository;

    @Autowired
    public BikePhotoService(BikePhotoRepository bikePhotoRepository) {
        this.bikePhotoRepository = bikePhotoRepository;
    }

    public void addChangeBikePhoto(BikePhoto bikePhoto) {
        bikePhotoRepository.save(bikePhoto);
    }
}

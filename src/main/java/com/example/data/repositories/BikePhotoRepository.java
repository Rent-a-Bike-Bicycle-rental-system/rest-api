package com.example.data.repositories;

import com.example.data.data.BikePhoto;
import org.springframework.data.repository.CrudRepository;

public interface BikePhotoRepository extends CrudRepository<BikePhoto, Integer> {
    void deleteBikePhotoByBikeId(int bikeId);
    void deleteAllByBikeId(int bikeId);
    boolean deleteBikePhotoById(int id);
}

package com.example.data.repositories;

import com.example.data.data.BikePhoto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BikePhotoRepository extends CrudRepository<BikePhoto, Integer> {
    void deleteBikePhotoByBikeId(int bikeId);

    @Query("DELETE FROM BikePhoto bp WHERE bp.bikeId = :id")
    @Modifying
    void deleteBikePhotosByBikeId(@Param("id") int id);
    void deleteBikePhotoById(int id);
}

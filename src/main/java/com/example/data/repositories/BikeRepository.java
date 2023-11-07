package com.example.data.repositories;


import com.example.data.data.Bike;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends CrudRepository<Bike, Integer> {
    @Query("SELECT b FROM Bike b")
    List<Bike> getAllBikes();
    void deleteBikeById(int id);
}

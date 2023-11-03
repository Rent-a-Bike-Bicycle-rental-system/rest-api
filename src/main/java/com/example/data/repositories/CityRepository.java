package com.example.data.repositories;

import com.example.data.data.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Integer> {

    @Query("SELECT c FROM City c")
    List<City> getAllCities();
    boolean deleteById(int id);
}

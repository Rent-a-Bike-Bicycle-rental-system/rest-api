package com.example.data.services;

import com.example.data.data.City;
import com.example.data.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public boolean addNewCity(City city) {
        Optional<City> existingCity = cityRepository.findById(city.getId());

        if(existingCity.isEmpty()) {
            cityRepository.save(city);

            return true;
        } else
            return false;
    }
}

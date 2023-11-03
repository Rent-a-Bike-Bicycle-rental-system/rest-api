package com.example.data.services;

import com.example.data.data.Application;
import com.example.data.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public boolean addNewApplication(Application application) {
        Optional<Application> existingBike = applicationRepository.findById(application.getId());

        if (existingBike.isEmpty()) {
            applicationRepository.save(application);

            return true;
        } else {
            return false;
        }
    }
}

package com.example.data.database;

import com.example.data.data.*;
import com.example.data.repositories.*;
import com.example.data.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostgreSqlDatabase implements DatabaseInterface {
    private final BikeRepository bikeRepository;
    private final BikeService bikeService;

    private final CityRepository cityRepository;
    private final CityService cityService;

    private final ApplicationRepository applicationRepository;
    private final ApplicationService applicationService;

    private final AdminRepository adminRepository;
    private final AdminService adminService;

    private final BikePhotoRepository bikePhotoRepository;
    private final BikePhotoService bikePhotoService;


    @Autowired
    public PostgreSqlDatabase(BikeRepository bikeRepository, BikeService bikeService, CityRepository cityRepository, CityService cityService, ApplicationRepository applicationRepository, ApplicationService applicationService, AdminRepository adminRepository, AdminService adminService, BikePhotoRepository bikePhotoRepository, BikePhotoService bikePhotoService) {
        this.bikeRepository = bikeRepository;
        this.bikeService = bikeService;

        this.cityRepository = cityRepository;
        this.cityService = cityService;

        this.applicationRepository = applicationRepository;
        this.applicationService = applicationService;

        this.adminRepository = adminRepository;
        this.adminService = adminService;
        this.bikePhotoRepository = bikePhotoRepository;
        this.bikePhotoService = bikePhotoService;
    }

    @Override
    public List<Bike> getBikes() {
        return bikeRepository.getAllBikes();
    }

    @Override
    @Transactional
    public boolean addNewBike(Bike bike) {
        List<BikePhoto> photosList = new ArrayList<>(bike.getPhotos());
        bike.setPhotos(new ArrayList<>());

        int bikeId = bikeService.addNewBike(bike);
        if(bikeId == Integer.MIN_VALUE)
            return false;

        for(BikePhoto bikePhoto: photosList) {
            bikePhoto.setBikeId(bikeId);
            bikePhoto.setId(-1);
            bikePhotoService.addChangeBikePhoto(bikePhoto);
        }

        return true;
    }

    @Override
    @Transactional
    public boolean deleteBike(int id) {
        bikePhotoRepository.deleteBikePhotosByBikeId(id);
        bikeRepository.deleteBikeById(id);

        return true;
    }

    @Override
    @Transactional
    public boolean changeBikeInfo(Bike bike) {
        bikePhotoRepository.deleteBikePhotosByBikeId(bike.getId());
        bikeService.updateBike(bike);

        return true;
    }

    @Override
    public List<City> getCities() {
        return cityRepository.getAllCities();
    }

    @Override
    public boolean addNewCity(City city) {
        return cityService.addNewCity(city);
    }

    @Override
    public boolean deleteCity(int id) {
        cityRepository.deleteById(id);

        return true;
    }

    @Override
    public List<Application> getApplications(ApplicationRequest request) {
        int length = request.length();
        int from = request.from();
        int to = request.to();

        if(from != Integer.MIN_VALUE && to != 0)
            return applicationRepository.getApplicationsByIdBetween(from, to);

        else if (length != Integer.MIN_VALUE && to != 0) {
            from = Math.min((to - length), 1);
            return applicationRepository.getApplicationsByIdBetween(from, to);
        }

        else if (length != Integer.MIN_VALUE && from != 0) {
            return applicationRepository.getApplicationsByIdBetween(from, from + length);
        }

        else if (length != 0) {
            int lastId = applicationRepository.getLastId();
            return applicationRepository.getApplicationsByIdBetween(lastId-length, lastId);
        }
        else
            return null;
    }

    @Override
    public void addNewApplication(Application application) {
        applicationService.addNewApplication(application);
    }

    @Override
    public Admin getAdminByLogin(String login) {
        return adminRepository.getAdminByLogin(login);
    }

    @Override
    public boolean registerAdmin(Admin admin) {
        return adminService.registerNewAdmin(admin);
    }

    @Override
    public boolean isAdminLoginExists(String login) {
        return adminRepository.existsAdminByLogin(login);
    }

}

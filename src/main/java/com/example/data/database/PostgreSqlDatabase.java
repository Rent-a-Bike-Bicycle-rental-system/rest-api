package com.example.data.database;

import com.example.data.data.*;
import com.example.data.repositories.AdminRepository;
import com.example.data.repositories.ApplicationRepository;
import com.example.data.repositories.BikeRepository;
import com.example.data.repositories.CityRepository;
import com.example.data.services.AdminService;
import com.example.data.services.ApplicationService;
import com.example.data.services.BikeService;
import com.example.data.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Autowired
    public PostgreSqlDatabase(BikeRepository bikeRepository, BikeService bikeService, CityRepository cityRepository, CityService cityService, ApplicationRepository applicationRepository, ApplicationService applicationService, AdminRepository adminRepository, AdminService adminService) {
        this.bikeRepository = bikeRepository;
        this.bikeService = bikeService;

        this.cityRepository = cityRepository;
        this.cityService = cityService;

        this.applicationRepository = applicationRepository;
        this.applicationService = applicationService;

        this.adminRepository = adminRepository;
        this.adminService = adminService;
    }

    @Override
    public List<Bike> getBikes() {
        return bikeRepository.getAllBikes();
    }

    @Override
    public boolean addNewBike(Bike bike) {
        return bikeService.addNewBike(bike);
    }

    @Override
    public boolean deleteBike(int id) {
        return bikeRepository.deleteBikeById(id);
    }

    @Override
    public boolean changeBikeInfo(Bike bike) {
        return bikeService.updateBike(bike);
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
        return cityRepository.deleteById(id);
    }

    @Override
    public List<Application> getApplications(ApplicationReqest request) {
        int length = request.length();
        int from = request.from();
        int to = request.to();

        if(from != Integer.MIN_VALUE && to != Integer.MIN_VALUE)
            return applicationRepository.getApplicationsByIdBetween(from, to);

        else if (length != Integer.MIN_VALUE && to != Integer.MIN_VALUE) {
            from = Math.min((to - length), 1);
            return applicationRepository.getApplicationsByIdBetween(from, to);
        }

        else if (length != Integer.MIN_VALUE && from != Integer.MIN_VALUE) {
            return applicationRepository.getApplicationsByIdBetween(from, from + length);
        }

        else if (length != Integer.MIN_VALUE) {
            int lastId = applicationRepository.getLastId();
            return applicationRepository.getApplicationsByIdBetween(lastId-length, lastId);
        }
        else
            return null;
    }

    @Override
    public boolean addNewApplication(Application application) {
        return applicationService.addNewApplication(application);
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

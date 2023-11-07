package com.example.data.database;

import com.example.data.data.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DatabaseInterface {
    List<Bike> getBikes();
    boolean addNewBike(Bike bike);
    boolean deleteBike(int id);
    boolean changeBikeInfo(Bike bike);

    List<City> getCities();
    boolean addNewCity(City city);
    boolean deleteCity(int id);

    List<Application> getApplications(ApplicationReqest reqest);
    void addNewApplication(Application application);

    Admin getAdminByLogin(String login);
    boolean registerAdmin(Admin admin);
    boolean isAdminLoginExists(String login);
}

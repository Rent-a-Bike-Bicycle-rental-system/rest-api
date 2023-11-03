package com.example.data.repositories;

import com.example.data.data.Application;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplicationRepository extends CrudRepository<Application, Integer> {
    List<Application> getApplicationsByIdBetween(int id, int id2);

    @Query("SELECT MAX(a.id) FROM Application a")
    int getLastId();
}

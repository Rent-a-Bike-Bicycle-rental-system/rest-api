package com.example.data.repositories;

import com.example.data.data.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
    Admin getAdminByLogin(String login);
    boolean existsAdminByLogin(String login);
}

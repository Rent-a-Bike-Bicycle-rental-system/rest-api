package com.example.data.services;

import com.example.data.data.Admin;
import com.example.data.repositories.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public boolean registerNewAdmin(Admin admin) {
        Optional<Admin> existingBike = adminRepository.findById(admin.getId());

        if (existingBike.isEmpty()) {
            adminRepository.save(admin);

            return true;
        } else {
            return false;
        }
    }
}

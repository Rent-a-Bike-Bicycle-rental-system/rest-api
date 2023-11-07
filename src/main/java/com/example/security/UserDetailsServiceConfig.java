package com.example.security;

import com.example.data.data.Admin;
import com.example.data.database.DatabaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@Configuration
public class UserDetailsServiceConfig {

    private final DatabaseInterface databaseInterface;

    @Autowired
    public UserDetailsServiceConfig(DatabaseInterface databaseInterface) {
        this.databaseInterface = databaseInterface;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = databaseInterface.getAdminByLogin(username);
            if (admin != null) {
                return new User(admin.getLogin(), admin.getPassword(), Collections.emptyList());
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        };
    }
}

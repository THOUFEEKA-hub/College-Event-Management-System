package com.college.eventapp;

import com.college.eventapp.model.User;
import com.college.eventapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin1@college.edu").isEmpty()) {
            userRepository.save(new User("Staff Admin 1", "admin1@college.edu", "admin123", "ADMIN"));
        }
        if (userRepository.findByEmail("admin2@college.edu").isEmpty()) {
            userRepository.save(new User("Staff Admin 2", "admin2@college.edu", "admin123", "ADMIN"));
        }
    }
}

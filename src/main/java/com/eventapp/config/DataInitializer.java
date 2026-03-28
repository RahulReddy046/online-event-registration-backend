package com.eventapp.config;

import com.eventapp.model.User;
import com.eventapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByEmail("admin@eventapp.com")) {
                User admin = new User();
                admin.setName("Admin User");
                admin.setEmail("admin@eventapp.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setPhone("0000000000");
                admin.setRole(User.Role.SUPER_ADMIN);
                admin.setActive(true);
                userRepository.save(admin);
                System.out.println("✅ Admin user created!");
            }
        };
    }
}
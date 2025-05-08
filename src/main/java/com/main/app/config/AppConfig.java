package com.main.app.config;

import com.main.app.entity.JobRole;
import com.main.app.repository.JobRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner initDefaultData(JobRoleRepository jobRoleRepository) {
        return args -> {
            List<String> predefinedRoles = List.of(
                    "Software Developer",
                    "Senior Manager",
                    "Product Manager",
                    "UI/UX Designer",
                    "Data Scientist"
            );
            predefinedRoles.forEach(roleName -> {
                jobRoleRepository.findRoleByName(roleName)
                        .ifPresentOrElse(
                                avoid -> {},
                                () -> {
                                   jobRoleRepository.save(
                                           JobRole.builder()
                                                   .name(roleName)
                                                   .isCustom(false)
                                                   .build()
                                   );
                                }
                        );
            });
        };
    }
}

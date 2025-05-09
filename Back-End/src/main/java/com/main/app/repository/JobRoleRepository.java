package com.main.app.repository;

import com.main.app.entity.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRoleRepository extends JpaRepository<JobRole, Long> {
    Optional<JobRole> findRoleByName(String name);
    @Query("SELECT roles FROM job_roles as roles WHERE roles.isCustom = :value")
    List<JobRole> findByIsCustom(boolean value);
}

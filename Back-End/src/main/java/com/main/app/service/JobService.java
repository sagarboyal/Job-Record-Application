package com.main.app.service;

import com.main.app.dto.JobDTO;
import com.main.app.payload.response.JobResponse;

import java.time.LocalDate;


public interface JobService {
    JobDTO createJob(JobDTO jobDTO);
    JobDTO updateJob(JobDTO jobDTO);
    JobDTO deleteJob(Long id);
    JobResponse getJobs(String status, String company, String role, LocalDate start, LocalDate end,
                        Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    JobDTO updateStatus(Long id, String status);
    JobDTO updateRole(Long id, String roleName);
}

package com.main.app.service;

import com.main.app.dto.JobDTO;
import com.main.app.payload.response.JobResponse;


public interface JobService {
    JobDTO createJob(JobDTO jobDTO);
    JobDTO updateJob(JobDTO jobDTO);
    JobDTO deleteJob(Long id);
    JobResponse getJobs(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    JobDTO updateStatus(Long id, String status);
    JobDTO updateRole(Long id, String roleName);
}

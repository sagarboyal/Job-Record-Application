package com.main.app.service;

import com.main.app.entity.Job;
import com.main.app.dto.JobDTO;

import java.util.List;

public interface JobService {
    JobDTO createJob(JobDTO jobDTO);
    Job updateJob(Job job);
    Job deleteJob(Long id);
    List<JobDTO> getJobs();
    JobDTO updateStatus(Long id, String status);
}

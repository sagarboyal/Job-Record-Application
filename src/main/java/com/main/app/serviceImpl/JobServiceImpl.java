package com.main.app.serviceImpl;

import com.main.app.entity.Job;
import com.main.app.entity.JobRole;
import com.main.app.entity.JobStatus;
import com.main.app.exception.ResourceNotFoundException;
import com.main.app.dto.JobDTO;
import com.main.app.repository.JobRepository;
import com.main.app.repository.JobRoleRepository;
import com.main.app.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobRoleRepository jobRoleRepository;

    @Override
    public JobDTO createJob(JobDTO jobDTO) {
        JobRole jobRole = jobRoleRepository.findRoleByName(jobDTO.getRoleName().toLowerCase())
                .orElseGet(() -> jobRoleRepository.save(
                        JobRole.builder()
                                .name(jobDTO.getRoleName().toLowerCase())
                                .isCustom(true)
                                .build()
                ));

        Job job = mapToJob(jobDTO);
        job.setStatus(JobStatus.APPLIED);
        job.setRole(jobRole);
        return mapToJobDTO(jobRepository.save(job));
    }

    @Override
    public Job updateJob(Job job) {
        Job dbJob = findJobById(job.getId());

        dbJob.setCompany(job.getCompany() != null ? job.getCompany() : dbJob.getCompany());
        dbJob.setSource(job.getSource() != null ? job.getSource() : dbJob.getSource());
        dbJob.setNotes(job.getNotes() != null ? job.getNotes() : dbJob.getNotes());
        dbJob.setStatus(job.getStatus() != null ? job.getStatus() : dbJob.getStatus());

        return jobRepository.save(dbJob);
    }

    @Override
    public Job deleteJob(Long id) {
        Job dbJob = findJobById(id);
        jobRepository.delete(dbJob);
        return dbJob;
    }

    @Override
    public List<JobDTO> getJobs() {
        List<Job> jobs = jobRepository.findAll();
        if (jobs.isEmpty()) {
            throw new ResourceNotFoundException("No Jobs found");
        }
        return jobs.stream().map(this::mapToJobDTO).toList();
    }

    @Override
    public JobDTO updateStatus(Long id, String status) {
        Job job = findJobById(id);
        job.setStatus(JobStatus.valueOf(status.toUpperCase()));
        return mapToJobDTO(jobRepository.save(job));
    }

    private Job findJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job with id : "+ id +" not found!"));
    }

    private Job mapToJob(JobDTO jobDTO) {
        return Job.builder()
                .id(jobDTO.getId())
                .company(jobDTO.getCompany())
                .source(jobDTO.getSource())
                .appliedAt(jobDTO.getAppliedAt())
                .notes(jobDTO.getNotes())
                .url(jobDTO.getUrl())
                .build();
    }

    private JobDTO mapToJobDTO(Job job) {
        return JobDTO.builder()
                .id(job.getId())
                .company(job.getCompany())
                .source(job.getSource())
                .status(job.getStatus().name())
                .roleName(job.getRole().getName())
                .appliedAt(job.getAppliedAt())
                .notes(job.getNotes())
                .url(job.getUrl())
                .build();
    }
}

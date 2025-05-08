package com.main.app.serviceImpl;

import com.main.app.entity.Job;
import com.main.app.entity.JobRole;
import com.main.app.entity.JobStatus;
import com.main.app.exception.ResourceNotFoundException;
import com.main.app.dto.JobDTO;
import com.main.app.payload.response.JobResponse;
import com.main.app.repository.JobRepository;
import com.main.app.repository.JobRoleRepository;
import com.main.app.service.JobService;
import com.main.app.specification.JobSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public JobDTO updateJob(JobDTO jobDTO) {
        Job dbJob = findJobById(jobDTO.getId());

        dbJob.setCompany(jobDTO.getCompany() != null ? jobDTO.getCompany() : dbJob.getCompany());
        dbJob.setSource(jobDTO.getSource() != null ? jobDTO.getSource() : dbJob.getSource());
        dbJob.setNotes(jobDTO.getNotes() != null ? jobDTO.getNotes() : dbJob.getNotes());
        dbJob.setUrl(jobDTO.getUrl() != null ? jobDTO.getUrl() : dbJob.getUrl());

        return mapToJobDTO(jobRepository.save(dbJob));
    }

    @Override
    public JobDTO deleteJob(Long id) {
        Job dbJob = findJobById(id);
        jobRepository.delete(dbJob);
        return mapToJobDTO(dbJob);
    }

    @Override
    public JobResponse getJobs(String status, String company, String role, LocalDate start, LocalDate end,
                               Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Specification<Job> spec = Specification.where(JobSpecification.hasStatus(convertStatus(status)))
                .and(JobSpecification.hasCompany(company))
                .and(JobSpecification.hasRole(role))
                .and(JobSpecification.appliedBetween(start, end));


        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);
        Page<Job> jobPage = jobRepository.findAll(spec, pageDetails);
        List<Job> jobs = jobPage.getContent();
        List<JobDTO> dtos = jobs.stream().map(this::mapToJobDTO).toList();
        if (jobs.isEmpty()) {
            throw new ResourceNotFoundException("No Jobs found");
        }
        return JobResponse.builder()
                .content(dtos)
                .totalPages(jobPage.getTotalPages())
                .totalElements(jobPage.getTotalElements())
                .pageNumber(jobPage.getNumber())
                .pageSize(jobPage.getSize())
                .lastPage(jobPage.isLast())
                .build();
    }

    @Override
    public JobDTO updateStatus(Long id, String status) {
        Job job = findJobById(id);
        job.setStatus(JobStatus.valueOf(status.toUpperCase()));
        return mapToJobDTO(jobRepository.save(job));
    }

    @Override
    public JobDTO updateRole(Long id, String roleName) {
        Job job = findJobById(id);
        JobRole jobRole = jobRoleRepository.findRoleByName(roleName.toLowerCase())
                .orElseGet(() -> jobRoleRepository.save(
                        JobRole.builder()
                                .name(roleName.toLowerCase())
                                .isCustom(true)
                                .build()
                ));

        job.setRole(jobRole);
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

    private JobStatus convertStatus(String status) {
        return status != null ? JobStatus.valueOf(status.toUpperCase()) : null;
    }
}

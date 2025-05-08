package com.main.app.controller;

import com.main.app.constants.PageConstants;
import com.main.app.dto.JobDTO;
import com.main.app.payload.response.JobResponse;
import com.main.app.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<JobResponse> getAllJobs(
            @RequestParam(name = "pageNumber", defaultValue = PageConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = PageConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY_JOB_ID, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = PageConstants.SORT_DIR, required = false) String sortOrder
    ) {
        return ResponseEntity.ok(jobService.getJobs(pageNumber, pageSize, sortBy, sortOrder));
    }

    @PostMapping
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobService.createJob(jobDTO));
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<JobDTO> updateJobStatus(@RequestParam String status,
                                                  @PathVariable Long id) {
        return ResponseEntity.ok(jobService.updateStatus(id, status));
    }

    @PatchMapping("/roles/{id}")
    public ResponseEntity<JobDTO> updateJobRole(@RequestParam String roleName,
                                                  @PathVariable Long id) {
        return ResponseEntity.ok(jobService.updateRole(id, roleName));
    }

    @PutMapping
    public ResponseEntity<JobDTO> updateJob(@RequestBody JobDTO jobDTO) {
        return ResponseEntity.ok(jobService.updateJob(jobDTO));
    }

    @DeleteMapping
    public ResponseEntity<JobDTO> deleteJob(Long id) {
        return ResponseEntity.ok(jobService.deleteJob(id));
    }
}

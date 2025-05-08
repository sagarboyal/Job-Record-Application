package com.main.app.controller;

import com.main.app.dto.JobDTO;
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
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        return ResponseEntity.ok(jobService.getJobs());
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
}

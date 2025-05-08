package com.main.app.serviceImpl;

import com.main.app.entity.Job;
import com.main.app.entity.JobStatus;
import com.main.app.repository.JobRepository;
import com.main.app.service.ExportService;
import com.main.app.specification.JobSpecification;
import com.main.app.util.CsvExportUtil;
import com.main.app.util.DocxExportUtil;
import com.main.app.util.PdfExportUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {
    private final JobRepository jobRepository;

    public ExportServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void exportPdf(String status, String company, String role, LocalDate start, LocalDate end,
                          HttpServletResponse response) throws IOException {
        Specification<Job> spec = Specification.where(JobSpecification.hasStatus(convertStatus(status)))
                .and(JobSpecification.hasCompany(company))
                .and(JobSpecification.hasRole(role))
                .and(JobSpecification.appliedBetween(start, end));

        List<Job> jobs = jobRepository.findAll(spec);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=jobs.pdf");

        PdfExportUtil.exportJobsToPdf(jobs, response.getOutputStream());
    }

    @Override
    public void exportCsv(String status, String company, String role, LocalDate startDate, LocalDate endDate, HttpServletResponse response) throws IOException {
        Specification<Job> spec = Specification.where(JobSpecification.hasStatus(convertStatus(status)))
                .and(JobSpecification.hasCompany(company))
                .and(JobSpecification.hasRole(role))
                .and(JobSpecification.appliedBetween(startDate, endDate));

        List<Job> jobs = jobRepository.findAll(spec);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=jobs.csv");

        CsvExportUtil.exportJobsToCsv(jobs, response.getWriter());

    }

    @Override
    public void exportDocx(String status, String company, String role, LocalDate startDate, LocalDate endDate, HttpServletResponse response) throws Exception {
        Specification<Job> spec = Specification.where(JobSpecification.hasStatus(convertStatus(status)))
                .and(JobSpecification.hasCompany(company))
                .and(JobSpecification.hasRole(role))
                .and(JobSpecification.appliedBetween(startDate, endDate));

        List<Job> jobs = jobRepository.findAll(spec);

        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=jobs.docx");

        DocxExportUtil.exportJobsToDocx(jobs, response.getOutputStream());
    }

    private JobStatus convertStatus(String status) {
        return status != null ? JobStatus.valueOf(status.toUpperCase()) : null;
    }
}

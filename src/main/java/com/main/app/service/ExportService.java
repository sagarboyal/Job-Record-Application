package com.main.app.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

public interface ExportService {
    void exportPdf(String status, String company, String role, LocalDate start, LocalDate end, HttpServletResponse response)throws IOException;
    void exportCsv(String status, String company, String role, LocalDate startDate, LocalDate endDate, HttpServletResponse response) throws IOException;
    void exportDocx(String status, String company, String role, LocalDate startDate, LocalDate endDate, HttpServletResponse response) throws Exception;
}

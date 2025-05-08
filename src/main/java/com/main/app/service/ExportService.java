package com.main.app.service;

import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

public interface ExportService {
    public void exportPdf(String status, String company, String role, LocalDate start, LocalDate end, HttpServletResponse response)throws Exception ;
}

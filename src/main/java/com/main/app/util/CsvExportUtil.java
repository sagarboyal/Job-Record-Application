package com.main.app.util;

import com.main.app.entity.Job;

import java.io.PrintWriter;
import java.util.List;

/**
 * Utility class to export a list of Job entities into a CSV format.
 */
public class CsvExportUtil {

    /**
     * Writes a list of jobs to a CSV file using the provided PrintWriter.
     *
     * @param jobs   List of Job entities to export
     * @param writer PrintWriter to write the CSV content
     */
    public static void exportJobsToCsv(List<Job> jobs, PrintWriter writer) {
        // Write CSV header
        writer.println("Company,Role,Status,Applied At,URL");

        // Write job data rows
        for (Job job : jobs) {
            writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                    escape(job.getCompany()),
                    escape(job.getRole().getName()),
                    escape(job.getStatus().toString()),
                    job.getAppliedAt(),
                    escape(job.getUrl()));
        }

        writer.flush();
    }

    /**
     * Escapes double quotes in CSV values.
     *
     * @param value Input string value
     * @return Escaped string suitable for CSV
     */
    private static String escape(String value) {
        if (value == null) return "";
        return value.replace("\"", "\"\"");
    }
}

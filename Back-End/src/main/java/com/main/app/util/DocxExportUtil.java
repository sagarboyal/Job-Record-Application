package com.main.app.util;

import com.main.app.entity.Job;
import org.apache.poi.xwpf.usermodel.*;

import java.io.OutputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DocxExportUtil {

    public static void exportJobsToDocx(List<Job> jobs, OutputStream outputStream) throws Exception {
        XWPFDocument document = new XWPFDocument();

        // Title
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Job Applications");
        titleRun.setBold(true);
        titleRun.setFontSize(16);

        document.createParagraph(); // Add an empty paragraph (spacing)

        // Table with 5 columns
        XWPFTable table = document.createTable();

        // Table header row
        XWPFTableRow headerRow = table.getRow(0); // First row created by default
        headerRow.getCell(0).setText("Company");
        headerRow.addNewTableCell().setText("Role");
        headerRow.addNewTableCell().setText("Status");
        headerRow.addNewTableCell().setText("Applied At");
        headerRow.addNewTableCell().setText("URL");

        // Data rows
        for (Job job : jobs) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(nullSafe(job.getCompany()));
            row.getCell(1).setText(nullSafe(job.getRole().getName()));
            row.getCell(2).setText(job.getStatus().toString());

            // Format appliedAt Date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = job.getAppliedAt() != null
                    ? job.getAppliedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
                    : "";
            row.getCell(3).setText(formattedDate);

            // Adding clickable URL (Hyperlink)
            String url = nullSafe(job.getUrl());
            if (!url.isEmpty()) {
                // Create a paragraph for the clickable link
                XWPFParagraph linkParagraph = row.getCell(4).addParagraph();
                XWPFRun linkRun = linkParagraph.createRun();
                linkRun.setText("Click here");
                linkRun.setColor("0000FF"); // Blue color for a link
                linkRun.setUnderline(UnderlinePatterns.SINGLE); // Use SINGLE underline pattern

                // Set the hyperlink target URL
                linkRun.setText(url);
            } else {
                row.getCell(4).setText("No Link");
            }
        }

        // Write the document content to the output stream
        document.write(outputStream);
        document.close();
    }

    // Helper method to ensure null-safe handling of strings
    private static String nullSafe(String val) {
        return val == null ? "" : val;
    }
}
